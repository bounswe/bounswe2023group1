package com.groupa1.resq.converter;

import com.groupa1.resq.dto.ProfileDto;
import com.groupa1.resq.entity.UserProfile;
import com.groupa1.resq.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileConverter {

    @Autowired
    ModelMapper modelMapper;

    public ProfileDto convertToDto(UserProfile userProfile) {
        if(userProfile == null) {
            throw new EntityNotFoundException("User profile not found");
        }
        ProfileDto profileDto = modelMapper.map(userProfile, ProfileDto.class);
        return profileDto;
    }

    public UserProfile convertToEntity(ProfileDto profileDto) {
        UserProfile userProfile = modelMapper.map(profileDto, UserProfile.class);
        return userProfile;
    }

}
