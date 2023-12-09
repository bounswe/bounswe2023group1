package com.groupa1.resq.entity;

import com.groupa1.resq.entity.enums.EEventType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table( name = "EVENT")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"reporter"})
@ToString(callSuper = true, exclude = {"reporter"})
public class Event extends BaseEntity {

    //@Enumerated(EnumType.STRING)
    //private EEventType eventType;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @Lob
    @Column(length = 3000)
    private String description;

    private LocalDateTime reportDate;

    private boolean isVerified;

    private BigDecimal eventLatitude;
    private BigDecimal eventLongitude;

}

