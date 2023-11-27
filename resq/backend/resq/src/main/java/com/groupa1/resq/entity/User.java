package com.groupa1.resq.entity;

import com.groupa1.resq.entity.enums.EUserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "USERS",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"userProfile", "requests", "needs", "resourcesReceived","resourcesSent", "tasksAssigned", "tasksAssignedTo", "feedbacks", "actions", "infos", "notifications"})
@ToString(callSuper = true, exclude = {"userProfile", "requests", "needs", "resourcesReceived","resourcesSent", "tasksAssigned", "tasksAssignedTo", "feedbacks", "actions", "infos", "notifications"})
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

    // Responders send, facilitators receive

    @OneToMany(fetch = FetchType.LAZY, mappedBy="sender")
    private Set<Resource> resourcesSent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="receiver")
    private Set<Resource> resourcesReceived;

    // Coordinator assigns to responder
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assigner")
    private Set<Task> tasksAssigned;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assignee")
    private Set<Task> tasksAssignedTo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reporter")
    private Set<Event> reportedEvents;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
    private Set<Feedback> feedbacks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "verifier")
    private Set<Action> actions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Info> infos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Notification> notifications;

    public User(String name, String surname, String email, String password) {
        this();
        this.name  = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public User() {
        this.requests = new HashSet<>();
        this.needs = new HashSet<>();
        this.resourcesSent = new HashSet<>();
        this.resourcesReceived = new HashSet<>();
        this.tasksAssigned = new HashSet<>();
        this.tasksAssignedTo = new HashSet<>();
        this.feedbacks = new HashSet<>();
        this.actions = new HashSet<>();
        this.infos = new HashSet<>();
        this.notifications = new HashSet<>();
    }
}
