package com.groupa1.resq.service;

import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.exception.NotOwnerException;
import com.groupa1.resq.repository.NeedRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateNeedRequest;
import com.groupa1.resq.request.UpdateNeedRequest;
import com.groupa1.resq.specification.NeedSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class NeedService {

    @Autowired
    NeedRepository needRepository;
    UserRepository userRepository;


    public void save(Long userId, CreateNeedRequest createNeedRequest) {
        User requester = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Need need = new Need();
        need.setRequester(requester);
        need.setDescription(createNeedRequest.getDescription());
        need.setLongitude(createNeedRequest.getLongitude());
        need.setLatitude(createNeedRequest.getLatitude());
        need.setQuantity(createNeedRequest.getQuantity());
        need.setCategoryTreeId(createNeedRequest.getCategoryTreeId());
        needRepository.save(need);
    }

    public List<Need> viewAllNeeds() {
        return needRepository.findAll();
    }

    public List<Need> viewNeedsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return needRepository.findByRequester(user);
    }

    public Need viewNeed(Long userId, Long needId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Need need = needRepository.findById(needId).orElseThrow(() -> new EntityNotFoundException("Need not found"));
        if(need.getRequester().getId() != user.getId()) {
            throw new NotOwnerException("User is not the owner of the need");
        }
        return need;
    }

    public void deleteNeed(Long userId, Long needId) {
        User requester = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Need need = needRepository.findById(needId).orElseThrow(() -> new EntityNotFoundException("Need not found"));
        if(need.getRequester().getId() != requester.getId()) {
            throw new NotOwnerException("User is not the owner of the need");
        }
        needRepository.deleteById(needId);
    }

    public void update(UpdateNeedRequest updateNeedRequest, Long userId, Long needId) {
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
        needRepository.save(need);
    }


    public List<Need> viewNeedsByFilter(BigDecimal longitude, BigDecimal latitude, String categoryTreeId, Long userId) {

        Specification<Need> spec = Specification.where(null);

        if (longitude != null && latitude != null) {
            spec = spec.and(NeedSpecifications.hasLongitude(longitude));
            spec = spec.and(NeedSpecifications.hasLatitude(latitude));
        }
        if (categoryTreeId != null) {
            spec = spec.and(NeedSpecifications.hasCategoryTreeId(categoryTreeId));
        }
        if (userId != null) {
            User requester = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
            spec = spec.and(NeedSpecifications.hasRequester(userId));
        }
        return needRepository.findAll(spec);



    }
}