package com.groupa1.resq.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@Table( name = "NEED")
@Data
public class Need extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    private String categoryTreeId;
    private String description;

    private Integer quantity;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

}
