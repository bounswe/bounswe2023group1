package com.groupa1.resq.entity;

import com.groupa1.resq.entity.enums.EGender;
import com.groupa1.resq.entity.enums.ESize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@Table( name = "RESOURCE")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"sender", "receiver"})
@ToString(callSuper = true)
public class Resource extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private String categoryTreeId;

    private EGender gender;

    // These field only for clothing
    @Enumerated(EnumType.STRING)
    private ESize size;

    private Integer quantity;

    private BigDecimal latitude;
    private BigDecimal longitude;

}
