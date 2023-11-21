package com.groupa1.resq.controller;

import com.groupa1.resq.entity.Resource;
import com.groupa1.resq.request.CreateResourceRequest;
import com.groupa1.resq.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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

    @PreAuthorize("hasRole('RESPONDER') or hasRole('COORDINATOR')")
    @GetMapping("/viewResource")
    public ResponseEntity<Resource> viewResource(@RequestParam Long resourceId) {
        log.info("Viewing resource with id: " + resourceId);
        return resourceService.viewResource(resourceId);
    }


    @PreAuthorize("hasRole('RESPONDER')")
    @PostMapping("/updateResource")
    public ResponseEntity<String> updateResource(@RequestBody CreateResourceRequest createResourceRequest, @RequestParam Long resourceId ) {
        log.info("Updating resource with request: " + createResourceRequest.toString());
        return resourceService.updateResource(createResourceRequest, resourceId);
    }
    @PreAuthorize("hasRole('COORDINATOR') or hasRole('RESPONDER')")
    @PostMapping("/deleteResource")
    public ResponseEntity<String> deleteResource(@RequestParam Long resourceId) {
        log.info("Deleting resource with id: " + resourceId);
        return resourceService.deleteResource(resourceId);
    }


    @PreAuthorize("hasRole('COORDINATOR')")
    @GetMapping("filterByDistance")
    public ResponseEntity<List<Resource>> filterByDistance(@RequestParam
                                                   BigDecimal longitude, @RequestParam BigDecimal latitude, @RequestParam BigDecimal distance) {
        log.info("Filtering resources by distance");
        return resourceService.filterByDistance(longitude, latitude, distance);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @GetMapping("filterByCategory")
    public ResponseEntity<List<Resource>> filterByCategory(@RequestParam(required = false) String categoryTreeId,
                                                           @RequestParam(required = false) BigDecimal longitude,
                                                             @RequestParam(required = false) BigDecimal latitude,
                                                           @RequestParam(required = false) Long userId) {
        log.info("Filtering resources by category");
        return resourceService.filterResource(latitude, longitude, categoryTreeId, userId);
    }
}
