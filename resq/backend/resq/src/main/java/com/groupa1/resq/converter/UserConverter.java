package com.groupa1.resq.converter;

import com.groupa1.resq.dto.UserDto;
import com.groupa1.resq.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class UserConverter {
    @Autowired
    private ModelMapper modelMapper;

    public UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    public User convertToEntity(UserDto userDto) throws ParseException {
        User post = modelMapper.map(userDto, User.class);
        return post;
    }
}
