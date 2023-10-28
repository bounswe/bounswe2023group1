package com.groupa1.resq.controller;

import com.groupa1.resq.config.ResqAppProperties;
import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.request.CreateNeedRequest;
import com.groupa1.resq.service.NeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/user")
public class NeedController {

    @Autowired
    private ResqAppProperties resqAppProperties;

    @Autowired
    private NeedService needService;

    @GetMapping("/getNeed")
    public String index() {
        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("An INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");
        return resqAppProperties.getServerPort();
    }

    @GetMapping("/viewNeedsByUser")
    @PreAuthorize("hasRole('FACILITATOR')")
    public List<Need> viewNeedsByUser(@RequestParam Long userId) {
        log.info("Viewing needs for userId: {}", userId);
        return needService.viewNeedsByUser(userId);
    }

    @GetMapping("/viewNeedsByLocation")
    @PreAuthorize("hasRole('FACILITATOR')")
    public List<Need> viewNeedsByLocation(@RequestParam BigDecimal longitude, @RequestParam BigDecimal latitude) {
        log.info("Viewing needs for location: {}, {}", longitude, latitude);
        return needService.viewNeedsByLocation(longitude, latitude);
    }

    @PostMapping("/createNeed")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR')")
    public String createNeed(@RequestBody CreateNeedRequest createNeedRequest) {
        log.info("Creating need for user: {}", createNeedRequest.getUserId());
        needService.save(createNeedRequest);
        return "Need successfully created.";
    }

    @GetMapping("/viewAllNeeds")
    @PreAuthorize("hasRole('FACILITATOR')")
    public List<Need> viewAllNeeds() {
        log.info("Viewing all needs");
        return needService.viewAllNeeds();
    }

    @GetMapping("/viewMyNeeds")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR')")
    public List<Need> viewMyNeed(@RequestParam Long userId, @RequestParam(required = false) Long needId) {
        log.info("Viewing needs for user: {}, need: {}", userId, needId);
        if (needId == null) {
            return needService.viewMyNeeds(userId);
        }
        return needService.viewMyNeed(userId, needId);
    }

    @PostMapping("/deleteNeedVictim")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR')")
    public String deleteNeed(@RequestParam Long userId, @RequestParam Long needId) {
        log.info("Deleting need for user: {}, need: {}", userId, needId);
        needService.deleteNeed(userId, needId);
        return "Need successfully deleted.";
    }






}