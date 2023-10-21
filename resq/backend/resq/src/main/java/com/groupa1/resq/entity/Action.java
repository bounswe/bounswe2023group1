package com.groupa1.resq.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table( name = "ACTION")
@Data
public class Action extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "verifier_id")
    private User verifier;

    @Lob
    @Column(length = 3000)
    private String description;

    private LocalDateTime dueDate;

    private boolean isCompleted;

    private BigDecimal startLatitude;
    private BigDecimal startLongitude;

    private BigDecimal endLatitude;
    private BigDecimal endLongitude;

}

