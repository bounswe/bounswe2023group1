package com.groupa1.resq.controller;

import com.groupa1.resq.config.ResqAppProperties;
import com.groupa1.resq.entity.Request;
import com.groupa1.resq.request.CreateReqRequest;
import com.groupa1.resq.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/viewRequestsByUser")
    @PreAuthorize("hasRole('FACILITATOR')")
    public List<Request> viewRequestsByUser(@RequestParam Long userId) {
        log.info("Viewing requests for userId: {}", userId);
        return requestService.viewRequestsByUser(userId);
    }

    @GetMapping("/viewRequestsByLocation")
    @PreAuthorize("hasRole('FACILITATOR')")
    public List<Request> viewRequestsByLocation(@RequestParam BigDecimal longitude, @RequestParam BigDecimal latitude) {
        log.info("Viewing requests for location: {}, {}", longitude, latitude);
        return requestService.viewRequestsByLocation(longitude, latitude);
    }

    @PostMapping("/createRequest")
    @PreAuthorize("hasRole('FACILITATOR')")
    public String createRequest(@RequestParam Long userId, @RequestBody CreateReqRequest createReqRequest) {
        log.info("Creating request for user: {}", userId);
        requestService.save(userId, createReqRequest);
        return "Request successfully created.";
    }

    @GetMapping("/viewAllRequests")
    @PreAuthorize("hasRole('FACILITATOR')")
    public List<Request> viewAllRequests() {
        log.info("Viewing all requests");
        return requestService.viewAllRequests();
    }






}