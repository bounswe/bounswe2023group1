package com.groupa1.resq.controller;

import com.groupa1.resq.config.ResqAppProperties;
import com.groupa1.resq.converter.UserConverter;
import com.groupa1.resq.dto.UserDto;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private UserConverter userConverter;

    @PostMapping("/requestRole")
    public String requestRole(@RequestParam Long userId, @RequestParam String role) {
        log.info("Requested role: {} requested for user: {}", role, userId);
        User user = userService.requestRole(userId, role);
        return "Role successfully inserted to " + user.getName() + ".";
    }

    @GetMapping("/getUserInfo")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR') or hasRole('FACILITATOR') or hasRole('RESPONDER') or hasRole('VICTIM')")
    public UserDto getUserInfo(@RequestParam Long userId) {
        log.info("Get user info requested for userId : {}", userId);
        return userConverter.convertToDto(userService.findById(userId));
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
