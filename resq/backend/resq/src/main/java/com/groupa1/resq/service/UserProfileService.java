package com.groupa1.resq.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupa1.resq.converter.ProfileConverter;
import com.groupa1.resq.dto.ProfileDto;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.UserProfile;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.repository.UserProfileRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.util.NullAwareBeanUtilsBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
@Slf4j
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NullAwareBeanUtilsBean beanUtils;

    @Autowired
    private ProfileConverter profileConverter;




    public UserProfile saveProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    public String updateProfile(Long userId, ProfileDto profileDto)
            throws InvocationTargetException, IllegalAccessException {

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        UserProfile userProfile = user.getUserProfile();
        ProfileDto updatedProfileDto = objectMapper.convertValue(profileDto, ProfileDto.class);
        UserProfile updatedProfile = profileConverter.convertToEntity(updatedProfileDto);
        beanUtils.copyProperties(userProfile, updatedProfile);
        user.setUserProfile(userProfile);
        userRepository.save(user);
        return "Profile successfully updated.";

    }
}
