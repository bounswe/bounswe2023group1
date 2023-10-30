package com.groupa1.resq.entity;

import com.groupa1.resq.entity.enums.EGender;
import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table( name = "REQUEST")
@Data
public class Request extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="request")
    private Set<Need> needs;

    @Enumerated(EnumType.STRING)
    private EUrgency urgency;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    private BigDecimal latitude;
    private BigDecimal longitude;
    private String description;

}
