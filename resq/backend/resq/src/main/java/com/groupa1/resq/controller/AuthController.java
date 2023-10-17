package com.groupa1.resq.controller;

import com.groupa1.resq.request.LoginUserRequest;
import com.groupa1.resq.request.RegisterUserRequest;
import com.groupa1.resq.response.JwtResponse;
import com.groupa1.resq.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Later, register/login processes will be forwarded to their corresponding User type's services for different
    // types of verifications.

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterUserRequest registerUserRequest) {
        log.info("Register user request {}", registerUserRequest.getEmail());
        return authService.signup(registerUserRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody LoginUserRequest loginUserRequest) {
        log.info("Login user request {}", loginUserRequest.getUsername());
        return authService.signin(loginUserRequest);
    }
}
