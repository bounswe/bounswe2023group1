package com.groupa1.resq.service;

import com.groupa1.resq.auth.UserDetailsImpl;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.UserProfile;
import com.groupa1.resq.entity.enums.EUserRole;
import com.groupa1.resq.request.LoginUserRequest;
import com.groupa1.resq.request.RegisterUserRequest;
import com.groupa1.resq.response.JwtResponse;
import com.groupa1.resq.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    JwtUtils jwtUtils;


    public ResponseEntity<String> signup(RegisterUserRequest registerUserRequest) {

        if (userService.existsByEmail(registerUserRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(registerUserRequest.getName(), registerUserRequest.getSurname(), registerUserRequest.getEmail(),
                encoder.encode(registerUserRequest.getPassword()));

        Set<EUserRole> roles = new HashSet<>();

        // Each registered user has VICTIM role by default.
        roles.add(EUserRole.VICTIM);
        user.setRoles(roles);
        UserProfile userProfile = new UserProfile();
        userProfile.setName(registerUserRequest.getName());
        userProfile.setSurname(registerUserRequest.getSurname());
        userProfile.setUser(user);
        userProfileService.saveProfile(userProfile);
        user.setUserProfile(userProfile);
        userService.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }


    public ResponseEntity<JwtResponse> signin(LoginUserRequest loginUserRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginUserRequest.getEmail(), loginUserRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).map(role -> role.split("_")[1]).collect(Collectors.toList());


        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getName(), userDetails.getSurname(), userDetails.getEmail(), roles));
    }
}
