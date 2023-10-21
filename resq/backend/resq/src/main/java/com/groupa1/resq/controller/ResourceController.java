package com.groupa1.resq.controller;

import com.groupa1.resq.request.CreateResourceRequest;
import com.groupa1.resq.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PreAuthorize("hasRole('RESPONDER')")
    @PostMapping("/createResource")
    public ResponseEntity<String> createResource(@RequestBody CreateResourceRequest createResourceRequest) {
        log.info("Creating resource with request: " + createResourceRequest.toString());
        return resourceService.createResource(createResourceRequest);
    }
}
