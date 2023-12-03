package com.groupa1.resq.controller;

import com.groupa1.resq.converter.ProfileConverter;
import com.groupa1.resq.dto.ProfileDto;
import com.groupa1.resq.service.UserProfileService;
import com.groupa1.resq.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private ProfileConverter profileConverter;

    @Autowired
    private UserService userService;


    @GetMapping("/getProfileInfo")
    @PreAuthorize("hasRole('FACILITATOR') or hasRole('COORDINATOR') or hasRole('RESPONDER') or hasRole('VICTIM')")
    public ProfileDto getProfileInfo(@RequestParam Long userId) {
        log.info("Get profile info requested for userId : {}", userId);
        return profileConverter.convertToDto(userService.findById(userId).getUserProfile());
    }


    @PostMapping("/updateProfile")
    @PreAuthorize("hasRole('FACILITATOR') or hasRole('COORDINATOR') or hasRole('RESPONDER') or hasRole('VICTIM')")
    public String updateProfile(@RequestParam Long userId, @RequestBody
    ProfileDto profileDto)  throws InvocationTargetException, IllegalAccessException{
        log.info("Updating profile for user: {}", userId);
        return userProfileService.updateProfile(userId, profileDto);

    }

}
