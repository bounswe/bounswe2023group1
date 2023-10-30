package com.groupa1.resq.service;

import com.groupa1.resq.entity.Request;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.repository.RequestRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateReqRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class RequestService {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserRepository userRepository;

    public List<Request> viewRequestsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return requestRepository.findByRequester(user);
    }

    public List<Request> viewRequestsByLocation(BigDecimal longitude, BigDecimal latitude) {
        return requestRepository.findByLongitudeAndLatitude(longitude, latitude);
    }

    public void save(Long userId, CreateReqRequest createReqRequest) {
        User requester = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Request request = new Request();
        request.setRequester(requester);
        request.setDescription(createReqRequest.getDescription());
        request.setLongitude(createReqRequest.getLongitude());
        request.setLatitude(createReqRequest.getLatitude());
        request.setNeeds(createReqRequest.getNeeds());
        requestRepository.save(request);
    }

    public List<Request> viewAllRequests() {
        return requestRepository.findAll();
    }

}