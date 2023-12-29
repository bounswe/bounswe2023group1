package com.groupa1.resq.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@NoArgsConstructor
@Entity
@Table( name = "FEEDBACK")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"task", "creator"})
@ToString(callSuper = true)
public class Feedback extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(length = 2048)
    private String message;
}
