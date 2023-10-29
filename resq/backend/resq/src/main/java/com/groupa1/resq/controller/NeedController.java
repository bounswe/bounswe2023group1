package com.groupa1.resq.controller;

import com.groupa1.resq.config.ResqAppProperties;
import com.groupa1.resq.entity.Need;
import com.groupa1.resq.request.CreateNeedRequest;
import com.groupa1.resq.request.UpdateNeedRequest;
import com.groupa1.resq.service.NeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PreAuthorize("hasRole('FACILITATOR')")
    public List<Need> viewNeedsByFilter(@RequestParam(required = false) BigDecimal longitude,
                                        @RequestParam(required = false) BigDecimal latitude,
                                        @RequestParam(required = false) String categoryTreeId,
                                        @RequestParam(required = false) Long userId) {
        log.info("Viewing needs for location: {}, {}, category: {}, user: {}", longitude, latitude, categoryTreeId, userId);
        return needService.viewNeedsByFilter(longitude, latitude, categoryTreeId, userId);
    }

    @PostMapping("/createNeed")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR')")
    public String createNeed(@RequestParam Long userId, @RequestBody CreateNeedRequest createNeedRequest) {
        log.info("Creating need for user: {}", userId);
        needService.save(userId, createNeedRequest);
        return "Need successfully created.";
    }

    @GetMapping("/viewAllNeeds")
    @PreAuthorize("hasRole('FACILITATOR')")
    public List<Need> viewAllNeeds() {
        log.info("Viewing all needs");
        return needService.viewAllNeeds();
    }

    @GetMapping("/viewNeed")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR')")
    public Need viewNeed(@RequestParam Long userId, @RequestParam Long needId) {
        log.info("Viewing need with id: {}", needId);
        return needService.viewNeed(userId, needId);
    }

    @GetMapping("/viewNeedsByUserId")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR')")
    public List<Need> viewNeedsByUserId(@RequestParam Long userId) {
        log.info("Viewing needs for user: {}", userId);
        return needService.viewNeedsByUserId(userId);
    }

    @PostMapping("/deleteNeed")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR')")
    public String deleteNeed(@RequestParam Long userId, @RequestParam Long needId) {
        log.info("Deleting need for user: {}, need: {}", userId, needId);
        needService.deleteNeed(userId, needId);
        return "Need successfully deleted.";
    }

    @PostMapping("/updateNeed")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR')")
    public String updateNeed(@RequestBody UpdateNeedRequest updateNeedRequest, @RequestParam Long userId, @RequestParam Long needId) {
        log.info("Updating need for user: {}, need: {}", userId, needId);
        needService.update(updateNeedRequest, userId, needId);
        return "Need successfully updated.";
    }

}