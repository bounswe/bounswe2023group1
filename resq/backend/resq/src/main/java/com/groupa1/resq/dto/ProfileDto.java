package com.groupa1.resq.dto;


import com.groupa1.resq.entity.enums.EGender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileDto {

    private String name;
    private String surname;

    private LocalDate birth_date;

    private EGender gender;

    private boolean isEmailConfirmed;

    private String phoneNumber;

    private boolean isPrivacyPolicyAccepted;

    private String Country;

    private String City;

    private String State;

    private String bloodType;

    private Integer weight;

    private Integer height;
}
