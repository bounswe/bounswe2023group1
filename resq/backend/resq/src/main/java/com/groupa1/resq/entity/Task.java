package com.groupa1.resq.entity;

import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Entity
@Table( name = "TASK")
@Data
public class Task extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "assigner_id")
    private User assigner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="task")
    private Set<Action> actions;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Resource> resources;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="task")
    private Set<Feedback> feedbacks;

    @Enumerated(EnumType.STRING)
    private EUrgency urgency;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @Lob
    @Column(length = 3000)
    private String description;

}
