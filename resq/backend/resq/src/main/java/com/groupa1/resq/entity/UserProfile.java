package com.groupa1.resq.entity;

import com.groupa1.resq.entity.enums.EGender;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@NoArgsConstructor
@Entity
@Table( name = "USER_PROFILE")
@Data
public class UserProfile extends BaseEntity{

    private String name;
    private String surname;

    private LocalDate birth_date;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isEmailConfirmed;

    private String phoneNumber;

    private boolean isPrivacyPolicyAccepted;


}
