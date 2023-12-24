package com.groupa1.resq.controller;

import com.groupa1.resq.config.ResqAppProperties;
import com.groupa1.resq.dto.NeedDto;
import com.groupa1.resq.request.CreateNeedRequest;
import com.groupa1.resq.request.UpdateNeedRequest;
import com.groupa1.resq.service.NeedService;
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
@RequestMapping("/need")
public class NeedController {

    @Autowired
    private ResqAppProperties resqAppProperties;

    @Autowired
    private NeedService needService;

    @GetMapping("/viewNeedsByFilter")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR') or hasRole('COORDINATOR')")
    public ResponseEntity<List<NeedDto>> viewNeedsByFilter(@RequestParam(required = false) BigDecimal longitude1,
                                                           @RequestParam(required = false) BigDecimal latitude1,
                                                           @RequestParam(required = false) BigDecimal longitude2,
                                                           @RequestParam(required = false) BigDecimal latitude2,
                                                           @RequestParam(required = false) String categoryTreeId,
                                                           @RequestParam(required = false) Long userId) {
        log.info("Viewing needs for location: {}-{} / {}-{}, category: {}, user: {}", longitude1, latitude1, longitude2, latitude2, categoryTreeId, userId);
        return needService.viewNeedsByFilter(longitude1, latitude1, longitude2, latitude2, categoryTreeId, userId);
    }

    @PostMapping("/createNeed")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR')")
    public Long createNeed(@RequestParam Long userId, @RequestBody CreateNeedRequest createNeedRequest) {
        log.info("Creating need for user: {}", userId);
        return needService.save(userId, createNeedRequest);

    }

    @GetMapping("/viewAllNeeds")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR')")
    public ResponseEntity<List<NeedDto>> viewAllNeeds() {
        log.info("Viewing all needs");
        return needService.viewAllNeeds();
    }

    @GetMapping("/viewNeed")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR')")
    public ResponseEntity<NeedDto> viewNeed(@RequestParam Long userId, @RequestParam Long needId) {
        log.info("Viewing need with id: {}", needId);
        return needService.viewNeed(userId, needId);
    }

    @GetMapping("/viewNeedsByUserId")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR')")
    public ResponseEntity<List<NeedDto>> viewNeedsByUserId(@RequestParam Long userId) {
        log.info("Viewing needs for user: {}", userId);
        return needService.viewNeedsByUserId(userId);
    }

    @PostMapping("/deleteNeed")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR')")
    public ResponseEntity<String> deleteNeed(@RequestParam Long userId, @RequestParam Long needId) {
        log.info("Deleting need for user: {}, need: {}", userId, needId);
        return needService.deleteNeed(userId, needId);

    }

    @PostMapping("/updateNeed")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR')")
    public ResponseEntity<String> updateNeed(@RequestBody UpdateNeedRequest updateNeedRequest, @RequestParam Long userId, @RequestParam Long needId) {
        log.info("Updating need for user: {}, need: {}", userId, needId);
        return needService.update(updateNeedRequest, userId, needId);

    }

    @PostMapping("/cancelNeed")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR')")
    public ResponseEntity<String> cancelNeed(@RequestParam Long needId) {
        log.info("Cancelling  need: {}", needId);
        return needService.cancelNeed(needId);
    }

    @GetMapping("/filterByDistance")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR') or hasRole('COORDINATOR')")
    public ResponseEntity<List<NeedDto>> filterByDistance(@RequestParam BigDecimal longitude,
                                                       @RequestParam BigDecimal latitude,
                                                       @RequestParam BigDecimal distance) {
        log.info("Filtering needs by distance");
        return needService.filterByDistance(longitude, latitude, distance);
    }

}