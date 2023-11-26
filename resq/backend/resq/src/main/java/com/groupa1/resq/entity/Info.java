package com.groupa1.resq.entity;

import com.groupa1.resq.entity.enums.EUrgency;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@Table( name = "INFO")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"user"})
@ToString(callSuper = true)
public class Info extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    @Column(length = 3000)
    private String description;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @Enumerated(EnumType.STRING)
    private EUrgency urgency;

}
