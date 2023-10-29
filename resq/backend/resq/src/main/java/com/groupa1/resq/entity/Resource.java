package com.groupa1.resq.entity;

import com.groupa1.resq.entity.enums.EGender;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@Table( name = "RESOURCE")
@Data
public class Resource extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String categoryTreeId;

    private EGender gender;

    private Integer quantity;

    private BigDecimal latitude;
    private BigDecimal longitude;

}
