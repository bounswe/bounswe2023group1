package com.groupa1.resq.service;

import com.groupa1.resq.converter.RequestConverter;
import com.groupa1.resq.dto.RequestDto;
import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.Request;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.enums.ENeedStatus;
import com.groupa1.resq.entity.enums.ENotificationEntityType;
import com.groupa1.resq.entity.enums.ERequestStatus;
import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.exception.NotOwnerException;
import com.groupa1.resq.repository.NeedRepository;
import com.groupa1.resq.repository.RequestRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.AddNeedToReqRequest;
import com.groupa1.resq.request.CreateReqRequest;
import com.groupa1.resq.request.UpdateReqRequest;
import com.groupa1.resq.specification.RequestSpecifications;
import com.groupa1.resq.util.NotificationMessages;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class RequestService {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NeedRepository needRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    RequestConverter requestConverter;

    public void setNeedRepository(NeedRepository needRepository) {
        this.needRepository = needRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setRequestRepository(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public Long save(Long userId, CreateReqRequest createReqRequest) {
        User requester = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Request request = new Request();
        request.setRequester(requester);
        request.setDescription(createReqRequest.getDescription());
        request.setLongitude(createReqRequest.getLongitude());
        request.setLatitude(createReqRequest.getLatitude());
        request.setUrgency(createReqRequest.getUrgency());
        request.setStatus(ERequestStatus.PENDING);

        Set<Need> needSet = new HashSet<>(needRepository.findAllById(createReqRequest.getNeedIds()));
        request.setNeeds(needSet);
        Long requestId = requestRepository.save(request).getId();

        needSet.forEach(
                need ->
                {
                    need.setRequest(request);
                    String bodyMessage = String.format(NotificationMessages.REQUEST_CREATED_WITH_NEED, need.getId(), requester.getId(), request.getId());
                    notificationService.sendNotification("Request Created", bodyMessage, need.getRequester().getId(), request.getId() , ENotificationEntityType.REQUEST);
                }
        );
        needRepository.saveAll(needSet);
        return requestId;
    }

    public List<RequestDto> viewAllRequests() {
        return requestRepository.findAll().stream().map(request -> requestConverter.convertToDto(request)).toList();
    }

    public List<RequestDto> viewRequestsByFilter(BigDecimal longitude1, BigDecimal latitude1, BigDecimal longitude2, BigDecimal latitude2, EStatus status, EUrgency urgency, Long userId ) {

        Specification<Request> spec = Specification.where(null);

        if (longitude1 != null && latitude1 != null && longitude2 != null && latitude2 != null) {
            spec = spec.and(RequestSpecifications.isWithinRectangleScope(longitude1, longitude2, latitude1, latitude2));
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
        return requestRepository.findAll(spec).stream().map(request -> requestConverter.convertToDto(request)).toList();
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
        //request.setStatus(updateReqRequest.getStatus());
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

    public ResponseEntity<List<RequestDto>> filterByDistance(BigDecimal longitude,
                                                          BigDecimal latitude,
                                                          BigDecimal distance) {
        return ResponseEntity.ok(requestRepository.filterByDistance(longitude, latitude, distance).stream().map(request -> requestConverter.convertToDto(request)).toList());
    }

    @Transactional
    public ResponseEntity<String> addNeedToRequest(AddNeedToReqRequest addNeedToReqRequest){
        Long requestId = addNeedToReqRequest.getRequestId();
        List<Long> needId = addNeedToReqRequest.getNeedIds();
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new EntityNotFoundException("Request not found"));
        Set<Need> needSet = new HashSet<>(needRepository.findAllById(needId));
        needSet.forEach(need -> {
            if (need.getRequest() != null) {
                throw new IllegalArgumentException("Need already has a request");
            }
            need.setRequest(request);
            request.getNeeds().add(need);
            need.setStatus(ENeedStatus.INVOLVED_REQUEST);
            // notify victim that need was added to request
            String bodyMessage = String.format(NotificationMessages.NEED_ADDED_TO_REQUEST, need.getId(), request.getId());
            notificationService.sendNotification("Need Added to Request", bodyMessage, need.getRequester().getId(), request.getId() , ENotificationEntityType.REQUEST);
            needRepository.save(need);
        });
        needRepository.saveAll(needSet);
        requestRepository.save(request);
        return ResponseEntity.ok("Needs added to request successfully");
    }

    @Transactional
    public ResponseEntity<String> removeNeedFromRequest(AddNeedToReqRequest addNeedToReqRequest){
        Long requestId= addNeedToReqRequest.getRequestId();
        List<Long> needIds = addNeedToReqRequest.getNeedIds();
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new EntityNotFoundException("Request not found"));
        Set<Need> needSet = new HashSet<>(needRepository.findAllById(needIds));
        needSet.forEach(need -> {
            need.setRequest(null);
            request.getNeeds().remove(need);
            need.setStatus(ENeedStatus.NOT_INVOLVED);
            // notify victim that need was removed from request
            String bodyMessage = String.format(NotificationMessages.NEED_REMOVED_FROM_REQUEST, need.getId(), request.getId());
            notificationService.sendNotification("Need Removed from Request", bodyMessage, need.getRequester().getId(), request.getId() , ENotificationEntityType.REQUEST);
        });
        needRepository.saveAll(needSet);
        requestRepository.save(request);
        return ResponseEntity.ok("Needs removed from request successfully");

    }



}