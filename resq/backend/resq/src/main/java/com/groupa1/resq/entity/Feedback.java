package com.groupa1.resq.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Entity
@Table( name = "FEEDBACK")
@Data
public class Feedback extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @Lob
    @Column(length = 3000)
    private String message;
}
