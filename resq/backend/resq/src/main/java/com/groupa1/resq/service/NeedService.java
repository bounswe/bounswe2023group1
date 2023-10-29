package com.groupa1.resq.service;

import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.repository.NeedRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateNeedRequest;
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


    public void save(CreateNeedRequest createNeedRequest) {
        User requester = userRepository.findById(createNeedRequest.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
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

    public List<Need> viewMyNeeds(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return needRepository.findByRequester(user);
    }

    public List<Need> viewMyNeed(Long userId, Long needId) {
        User requester = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Need> needs = needRepository.findByIdAndRequester(needId, requester);
        if (needs.isEmpty()) {
            throw new EntityNotFoundException("Need not found");
        }
        return needs;
    }

    public void deleteNeedVictim(Long userId, Long needId) {
        User requester = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        needRepository.deleteByIdAndRequester(needId, requester);
    }

    public void deleteNeedFacilitator(Long needId) {
        needRepository.deleteById(needId);
    }

    public void update(CreateNeedRequest createNeedRequest, Long needId) {
        Need need = needRepository.findById(needId).orElseThrow(() -> new EntityNotFoundException("Need not found"));
        need.setDescription(createNeedRequest.getDescription());
        need.setLongitude(createNeedRequest.getLongitude());
        need.setLatitude(createNeedRequest.getLatitude());
        need.setQuantity(createNeedRequest.getQuantity());
        need.setCategoryTreeId(createNeedRequest.getCategoryTreeId());
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