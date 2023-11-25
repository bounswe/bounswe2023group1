package com.groupa1.resq.dto;

import com.groupa1.resq.entity.enums.EUserRole;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private String email;
    private String name;
    private String surname;
    private Set<EUserRole> roles;
}
