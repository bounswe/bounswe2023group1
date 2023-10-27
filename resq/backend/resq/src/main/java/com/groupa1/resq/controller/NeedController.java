package com.groupa1.resq.controller;

import com.groupa1.resq.config.ResqAppProperties;
import com.groupa1.resq.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/user")
public class NeedController {

    @Autowired
    private ResqAppProperties resqAppProperties;

    @Autowired
    private UserService userService;

    @GetMapping("/getNeed")
    public String index() {
        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("An INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");
        return resqAppProperties.getServerPort();
    }

    @GetMapping("/viewNeed")
    @PreAuthorize("hasRole('VICTIM') or hasRole('FACILITATOR')")




}