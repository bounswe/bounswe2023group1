package com.groupa1.resq.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupa1.resq.dto.ResourceDto;
import com.groupa1.resq.entity.enums.EResourceStatus;
import com.groupa1.resq.request.CreateResourceRequest;
import com.groupa1.resq.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Object> createResource(@RequestPart(name = "createResourceRequest") CreateResourceRequest createResourceRequest,
                                                 @RequestPart(name = "file", required = false) MultipartFile file) {
        log.info("Creating resource with request: " + createResourceRequest.toString());
        return resourceService.createResource(createResourceRequest, file);
    }

    @PreAuthorize("hasRole('RESPONDER') or hasRole('COORDINATOR') or hasRole('VICTIM')")
    @GetMapping("/viewResource")
    public ResponseEntity<ResourceDto> viewResource(@RequestParam Long resourceId) {
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



    @PreAuthorize("hasRole('COORDINATOR') or hasRole('VICTIM') or hasRole('RESPONDER')")
    @GetMapping("/filterByDistance")
    public ResponseEntity<List<ResourceDto>> filterByDistance(@RequestParam
                                                   BigDecimal longitude, @RequestParam BigDecimal latitude, @RequestParam BigDecimal distance) {
        log.info("Filtering resources by distance");
        return resourceService.filterByDistance(latitude, longitude, distance);
    }

    @PreAuthorize("hasRole('COORDINATOR') or hasRole('VICTIM') or hasRole('RESPONDER')")
    @GetMapping("/filterByCategory")
    public ResponseEntity<List<ResourceDto>> filterByCategory(@RequestParam(required = false) String categoryTreeId,
                                                           @RequestParam(required = false) BigDecimal longitude,
                                                             @RequestParam(required = false) BigDecimal latitude,
                                                           @RequestParam(required = false) Long userId,
                                                            @RequestParam(required = false) EResourceStatus status,
                                                              @RequestParam(required = false) Long receiverId){
        log.info("Filtering resources by category");
        return resourceService.filterResource(latitude, longitude, categoryTreeId, userId, status, receiverId);
    }

    @PreAuthorize("hasRole('COORDINATOR') or hasRole('VICTIM') or hasRole('RESPONDER')")
    @GetMapping("/filterByCategoryRectangularScope")
    public ResponseEntity<List<ResourceDto>> filterByCategoryRectangularScope(@RequestParam(required = false) String categoryTreeId,
                                                           @RequestParam(required = false) BigDecimal longitude1,
                                                             @RequestParam(required = false) BigDecimal latitude1,
                                                             @RequestParam(required = false) BigDecimal longitude2,
                                                             @RequestParam(required = false) BigDecimal latitude2,
                                                             @RequestParam(required = false) Long userId,
                                                           @RequestParam(required = false) Long receiverId) {
        log.info("Filtering resources by category, rectangular scope");
        return resourceService.filterResourceRectangularScope(latitude1, longitude1, latitude2, longitude2, categoryTreeId, userId, receiverId);
    }
}
