package com.groupa1.resq.service;

import com.groupa1.resq.converter.NeedConverter;
import com.groupa1.resq.dto.NeedDto;
import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.Request;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.enums.ENeedStatus;
import com.groupa1.resq.entity.enums.ESize;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.exception.NotOwnerException;
import com.groupa1.resq.repository.NeedRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateNeedRequest;
import com.groupa1.resq.request.UpdateNeedRequest;
import com.groupa1.resq.response.NeedStatusResponse;
import com.groupa1.resq.specification.NeedSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NeedService {

    @Autowired
    NeedRepository needRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NeedConverter needConverter;

    public void setNeedConverter(NeedConverter needConverter) {
        this.needConverter = needConverter;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setNeedRepository(NeedRepository needRepository) {
        this.needRepository = needRepository;
    }

    public Long save(Long userId, CreateNeedRequest createNeedRequest) {
        User requester = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Need need = new Need();
        need.setRequester(requester);
        need.setDescription(createNeedRequest.getDescription());
        need.setLongitude(createNeedRequest.getLongitude());
        need.setLatitude(createNeedRequest.getLatitude());
        need.setQuantity(createNeedRequest.getQuantity());
        need.setCategoryTreeId(createNeedRequest.getCategoryTreeId());
        need.setSize(createNeedRequest.getSize());
        need.setStatus(ENeedStatus.NOT_INVOLVED);
        need.setIsRecurrent(createNeedRequest.getIsRecurrent());
        return needRepository.save(need).getId();

    }

    public ResponseEntity<List<NeedDto>> viewAllNeeds() {
        List<Need> needs = needRepository.findAll();
        List<NeedDto> needDtos= needs.stream().map(need -> needConverter.convertToDto(need)).toList();
        return ResponseEntity.ok(needDtos);
    }

    public ResponseEntity<List<NeedDto>> viewNeedsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<NeedDto> needDtos = needRepository.findByRequester(user).stream().map(need -> needConverter.convertToDto(need)).toList();
        return ResponseEntity.ok(needDtos);
    }

    public ResponseEntity<NeedDto> viewNeed(Long userId, Long needId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Need need = needRepository.findById(needId).orElseThrow(() -> new EntityNotFoundException("Need not found"));
        if(need.getRequester().getId() != user.getId()) {
            throw new NotOwnerException("User is not the owner of the need");
        }
        return ResponseEntity.ok(needConverter.convertToDto(need));
    }

    public ResponseEntity<String> deleteNeed(Long userId, Long needId) {
        User requester = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Need need = needRepository.findById(needId).orElseThrow(() -> new EntityNotFoundException("Need not found"));
        if(need.getRequester().getId() != requester.getId()) {
            throw new NotOwnerException("User is not the owner of the need");
        }
        needRepository.deleteById(needId);
        return ResponseEntity.ok("Need deleted successfully");
    }

    public ResponseEntity<String> update (UpdateNeedRequest updateNeedRequest, Long userId, Long needId) {
        Need need = needRepository.findById(needId).orElseThrow(() -> new EntityNotFoundException("Need not found"));
        User requester = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if(need.getRequester().getId() != requester.getId()) {
            throw new NotOwnerException("User is not the owner of the need");
        }
        need.setDescription(updateNeedRequest.getDescription());
        need.setLongitude(updateNeedRequest.getLongitude());
        need.setLatitude(updateNeedRequest.getLatitude());
        need.setQuantity(updateNeedRequest.getQuantity());
        need.setCategoryTreeId(updateNeedRequest.getCategoryTreeId());
        need.setSize(updateNeedRequest.getSize());
        need.setIsRecurrent(updateNeedRequest.getIsRecurent());
        needRepository.save(need);
        return ResponseEntity.ok("Need updated successfully");
    }


    public ResponseEntity<List<NeedDto>> viewNeedsByFilter(BigDecimal longitude1, BigDecimal latitude1, BigDecimal longitude2, BigDecimal latitude2, String categoryTreeId, Long userId) {

        Specification<Need> spec = Specification.where(null);

        if (longitude1 != null && latitude1 != null && longitude2 != null && latitude2 != null) {
            spec = spec.and(NeedSpecifications.isWithinRectangleScope(longitude1, longitude2, latitude1, latitude2));

        }
        if (categoryTreeId != null) {
            spec = spec.and(NeedSpecifications.hasCategoryTreeId(categoryTreeId));
        }
        if (userId != null) {
            User requester = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
            spec = spec.and(NeedSpecifications.hasRequester(userId));
        }
        return ResponseEntity.ok(needRepository.findAll(spec).stream().map(need -> needConverter.convertToDto(need)).toList());

    }

    public ResponseEntity<String> cancelNeed(Long needId) {
        Need need = needRepository.findById(needId).orElseThrow(() -> new EntityNotFoundException("Need not found"));
        need.setStatus(ENeedStatus.CANCELLED);
        needRepository.save(need);
        return ResponseEntity.ok("Need cancelled successfully");
    }

    public ResponseEntity<List<NeedDto>> filterByDistance(BigDecimal longitude,
                                                       BigDecimal latitude,
                                                       BigDecimal distance) {
        return ResponseEntity.ok(needRepository.filterByDistance(longitude, latitude, distance).stream().map(need -> needConverter.convertToDto(need)).toList());
    }


    public ResponseEntity<List<NeedStatusResponse>> viewMyNeedsStatus(Long userId) {
        User victim = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Need> needs = needRepository.findByRequester(victim);
        List<NeedStatusResponse> needStatusResponses = new ArrayList<>();
        needs.stream().forEach(need-> {
            NeedStatusResponse needStatusResponse = new NeedStatusResponse();
            needStatusResponse.setNeedId(need.getId());
            needStatusResponse.setNeedStatus(need.getStatus());
            if (need.getRequest() != null ) {
                Request request = need.getRequest();
                needStatusResponse.setRequestId(request.getId());
                needStatusResponse.setRequestStatus(request.getStatus());
                needStatusResponse.setRequestCreatorId(request.getRequester().getId());
                needStatusResponse.setRequestLongitude(request.getLongitude());
                needStatusResponse.setRequestLatitude(request.getLatitude());
            }
            needStatusResponses.add(needStatusResponse);
        });
        return ResponseEntity.ok(needStatusResponses);

    }
}


