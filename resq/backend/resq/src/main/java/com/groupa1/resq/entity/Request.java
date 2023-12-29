package com.groupa1.resq.entity;

import com.groupa1.resq.entity.enums.ERequestStatus;
import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"requester", "needs", "task"})
@ToString(callSuper = true)
public class Request extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="request", cascade = CascadeType.ALL)
    private Set<Need> needs;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Enumerated(EnumType.STRING)
    private EUrgency urgency;

    @Enumerated(EnumType.STRING)
    private ERequestStatus status;

    private BigDecimal latitude;
    private BigDecimal longitude;
    private String description;

    public Request() {
        this.needs = new HashSet<>();
    }

}
