package com.groupa1.resq.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.groupa1.resq.entity.enums.EGender;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;


@NoArgsConstructor
@Entity
@Table( name = "USER_PROFILE")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"user"})
@ToString(exclude = {"user"})
public class UserProfile extends BaseEntity{

    private String name;
    private String surname;

    private LocalDate birth_date;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

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
