package com.groupa1.resq.entity;

import com.groupa1.resq.entity.enums.EGender;
import com.groupa1.resq.entity.enums.ENeedStatus;
import com.groupa1.resq.entity.enums.ESize;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@Table( name = "NEED")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"requester", "request"})
@ToString(callSuper = true)
public class Need extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    private String categoryTreeId;
    private String description;

    private Integer quantity;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    // These field only for clothing
    @Enumerated(EnumType.STRING)
    private ESize size;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    @Enumerated(EnumType.STRING)
    private ENeedStatus status;

}
