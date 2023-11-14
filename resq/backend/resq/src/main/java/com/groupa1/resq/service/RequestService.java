package com.groupa1.resq.service;

import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.Request;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.exception.NotOwnerException;
import com.groupa1.resq.repository.RequestRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateReqRequest;
import com.groupa1.resq.request.UpdateReqRequest;
import com.groupa1.resq.specification.RequestSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

    public void save(Long userId, CreateReqRequest createReqRequest) {
        User requester = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Request request = new Request();
        request.setRequester(requester);
        request.setDescription(createReqRequest.getDescription());
        request.setLongitude(createReqRequest.getLongitude());
        request.setLatitude(createReqRequest.getLatitude());
        request.setNeeds(createReqRequest.getNeeds());
        request.setUrgency(createReqRequest.getUrgency());
        request.setStatus(createReqRequest.getStatus());
        requestRepository.save(request);
    }

    public List<Request> viewAllRequests() {
        return requestRepository.findAll();
    }

    public List<Request> viewRequestsByFilter(BigDecimal longitude, BigDecimal latitude, EStatus status, EUrgency urgency, Long userId) {

        Specification<Request> spec = Specification.where(null);

        if (longitude != null && latitude != null) {
            spec = spec.and(RequestSpecifications.hasLongitude(longitude));
            spec = spec.and(RequestSpecifications.hasLatitude(latitude));
        }
        if (status != null) {
            spec = spec.and(RequestSpecifications.hasStatus(status));
        }
        if (urgency != null) {
            spec = spec.and(RequestSpecifications.hasUrgency(urgency));
        }
        if (userId != null) {
            User requester = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
            spec = spec.and(RequestSpecifications.hasRequester(userId));
        }
        return requestRepository.findAll(spec);
    }

    public void update(UpdateReqRequest updateReqRequest, Long userId, Long requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new EntityNotFoundException("Request not found"));
        User requester = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if(request.getRequester().getId() != requester.getId()) {
            throw new NotOwnerException("User is not the owner of the request");
        }
        request.setDescription(updateReqRequest.getDescription());
        request.setLongitude(updateReqRequest.getLongitude());
        request.setLatitude(updateReqRequest.getLatitude());
        request.setStatus(updateReqRequest.getStatus());
        request.setUrgency(updateReqRequest.getUrgency());
        requestRepository.save(request);
    }

    public void deleteRequest(Long userId, Long needId) {
        User requester = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Request request = requestRepository.findById(needId).orElseThrow(() -> new EntityNotFoundException("Request not found"));
        if(request.getRequester().getId() != requester.getId()) {
            throw new NotOwnerException("User is not the owner of the request");
        }
        requestRepository.deleteById(needId);
    }

}