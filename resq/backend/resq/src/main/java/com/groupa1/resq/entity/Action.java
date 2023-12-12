package com.groupa1.resq.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table( name = "ACTION")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"task", "verifier"})
@ToString(callSuper = true)
public class Action extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "verifier_id")
    private User verifier;

    @Column(length = 2048)
    private String description;

    private LocalDateTime dueDate;

    private boolean isCompleted;
    private boolean isVerified;

    private BigDecimal startLatitude;
    private BigDecimal startLongitude;

    private BigDecimal endLatitude;
    private BigDecimal endLongitude;

}

