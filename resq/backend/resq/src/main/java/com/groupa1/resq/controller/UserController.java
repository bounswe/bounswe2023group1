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
public class UserController {

    @Autowired
    private ResqAppProperties resqAppProperties;

    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    public String index() {
        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("An INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");
        return resqAppProperties.getServerPort();
    }

    @PostMapping("/requestRole")
    public String requestRole(@RequestParam Long userId, @RequestParam String role) {
        log.info("Requested role: {} requested for user: {}", role, userId);
        userService.requestRole(userId, role);
        return "Role successfully inserted.";
    }

    // Example role-secured methods
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('FACILITATOR') or hasRole('RESPONDER') or hasRole('ADMIN') or hasRole('VICTIM') or hasRole('COORDINATOR')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/facilitator")
    @PreAuthorize("hasRole('FACILITATOR')")
    public String facilitatorAccess() {
        return "Facilitator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/responder")
    @PreAuthorize("hasRole('RESPONDER')")
    public String responderAccess() {
        return "Responder Board.";
    }

    @GetMapping("/victim")
    @PreAuthorize("hasRole('VICTIM')")
    public String victimAccess() {
        return "Victim Board.";
    }

    @GetMapping("/coordinator")
    @PreAuthorize("hasRole('COORDINATOR')")
    public String coordinatorAccess() {
        return "Coordinator Board.";
    }
}
