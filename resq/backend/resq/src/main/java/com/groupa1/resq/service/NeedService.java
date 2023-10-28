package com.groupa1.resq.service;

import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.repository.NeedRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateNeedRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class NeedService {

    @Autowired
    NeedRepository needRepository;
    UserRepository userRepository;

    public List<Need> viewNeedsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return needRepository.findByRequester(user);
    }

    public List<Need> viewNeedsByLocation(BigDecimal longitude, BigDecimal latitude) {
        return needRepository.findByLongitudeAndLatitude(longitude, latitude);
    }

    public void save(CreateNeedRequest createNeedRequest) {
        User requester = userRepository.findById(createNeedRequest.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Need need = new Need();
        need.setRequester(requester);
        need.setDescription(createNeedRequest.getDescription());
        need.setLongitude(createNeedRequest.getLongitude());
        need.setLatitude(createNeedRequest.getLatitude());
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

    public void delete(Long userId, Long needId) {

        needRepository.deleteById(needId);
    }



}