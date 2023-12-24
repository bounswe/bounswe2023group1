package com.groupa1.resq.entity;

import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "TASK")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"assignee", "assigner", "actions", "resources", "feedbacks"})
@ToString(callSuper = true)
public class Task extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "assigner_id")
    private User assigner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="task", cascade = CascadeType.ALL)
    private Set<Action> actions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "task", cascade = CascadeType.ALL)
    private Set<Resource> resources;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="task")
    private Set<Feedback> feedbacks;

    @Enumerated(EnumType.STRING)
    private EUrgency urgency;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @Column(length = 2048)
    private String description;

    public Task() {
        this.actions = new HashSet<>();
        this.resources = new HashSet<>();
        this.feedbacks = new HashSet<>();
    }

}
