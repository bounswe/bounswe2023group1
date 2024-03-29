package com.groupa1.resq.controller;

import com.groupa1.resq.auth.UserDetailsImpl;
import com.groupa1.resq.config.ResqAppProperties;
import com.groupa1.resq.dto.NeedDto;
import com.groupa1.resq.dto.RequestDto;
import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.Request;
import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import com.groupa1.resq.request.AddNeedToReqRequest;
import com.groupa1.resq.request.CreateReqRequest;
import com.groupa1.resq.request.UpdateReqRequest;
import com.groupa1.resq.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private ResqAppProperties resqAppProperties;

    @Autowired
    private RequestService requestService;

    @GetMapping("/viewRequestsByFilter")
    @PreAuthorize("hasRole('FACILITATOR')")
    public List<RequestDto> viewRequestsByFilter(@RequestParam(required = false) BigDecimal longitude1,
                                                 @RequestParam(required = false) BigDecimal latitude1,
                                                 @RequestParam(required = false) BigDecimal longitude2,
                                                 @RequestParam(required = false) BigDecimal latitude2,
                                                 @RequestParam(required = false) EStatus status,
                                                 @RequestParam(required = false) EUrgency urgency,
                                                 @RequestParam(required = false) Long userId) {
        log.info("Viewing requests for location: {}-{} / {}-{}, status: {}, urgency: {}, user: {}", longitude1, latitude1, longitude2, latitude2, status, urgency, userId);
        return requestService.viewRequestsByFilter(longitude1, latitude1, longitude2, latitude2, status, urgency, userId);
    }


    @PostMapping("/createRequest")
    @PreAuthorize("hasRole('FACILITATOR')")
    public Long createRequest(@RequestParam Long userId, @RequestBody CreateReqRequest createReqRequest) {
        log.info("Creating request for user: {}", userId);
        return requestService.save(userId, createReqRequest);

    }

    @GetMapping("/viewAllRequests")
    @PreAuthorize("hasRole('FACILITATOR') or hasRole('COORDINATOR')")
    public List<RequestDto> viewAllRequests() {
        log.info("Viewing all requests");
        return requestService.viewAllRequests();
    }

    @PostMapping("/updateRequest")
    @PreAuthorize("hasRole('FACILITATOR')")
    public String updateRequest(@RequestBody UpdateReqRequest updateReqRequest, @RequestParam Long userId, @RequestParam Long requestId) {
        log.info("Updating request for user: {}, request: {}", userId, requestId);
        requestService.update(updateReqRequest, userId, requestId);
        return "Request successfully updated.";
    }

    @PostMapping("/deleteRequest")
    @PreAuthorize("hasRole('FACILITATOR')")
    public String deleteRequest(@RequestParam Long userId, @RequestParam Long requestId) {
        log.info("Deleting request for user: {}, request: {}", userId, requestId);
        requestService.deleteRequest(userId, requestId);
        return "Request successfully deleted.";
    }

    @GetMapping("/filterByDistance")
    @PreAuthorize("hasRole('FACILITATOR') or hasRole('COORDINATOR')")
    public ResponseEntity<List<RequestDto>> filterByDistance(@RequestParam BigDecimal longitude,
                                                          @RequestParam BigDecimal latitude,
                                                          @RequestParam BigDecimal distance) {
        log.info("Filtering requests by distance");
        return requestService.filterByDistance(longitude, latitude, distance);
    }

    @PostMapping("/addNeedToRequest")
    @PreAuthorize("hasRole('FACILITATOR')")
    public ResponseEntity<String> addNeedToRequest(@RequestBody
                                                       AddNeedToReqRequest addNeedToReqRequest, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        log.info("Adding need to request by facilitator: {}", userId);
        return requestService.addNeedToRequest(addNeedToReqRequest);
    }

    @PostMapping("/removeNeedFromRequest")
    @PreAuthorize("hasRole('FACILITATOR')")
    public ResponseEntity<String> removeNeedFromRequest(@RequestBody AddNeedToReqRequest removeNeedFromRequest, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        log.info("Removing need from request by facilitator: {}", userId);
        return requestService.removeNeedFromRequest(removeNeedFromRequest);
    }






}