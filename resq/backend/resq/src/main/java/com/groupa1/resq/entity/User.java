package com.groupa1.resq.entity;

import com.groupa1.resq.entity.enums.EUserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table( name = "USERS",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
@Data
public class User extends BaseEntity {

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String surname;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ElementCollection(targetClass = EUserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<EUserRole> roles = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private UserProfile userProfile;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="requester")
    private Set<Request> requests;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="requester")
    private Set<Need> needs;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="owner")
    private Set<Resource> resources;

    // Coordinator assigns to responder
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assigner")
    private Set<Task> tasksAssigned;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assignee")
    private Set<Task> tasksAssignedTo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
    private Set<Feedback> feedbacks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "verifier")
    private Set<Action> actions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Info> infos;

    public User(String name, String surname, String email, String password) {
        this.name  = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
}
