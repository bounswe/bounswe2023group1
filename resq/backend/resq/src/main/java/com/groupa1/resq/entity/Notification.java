package com.groupa1.resq.entity;

import com.groupa1.resq.entity.enums.ENotificationEntityType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Entity
@Table( name = "NOTIFICATION")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"user"})
@ToString(callSuper = true)
public class Notification extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @Column(length = 2048)
    private String body;

    private boolean isRead;

    private Long relatedEntityId;

    @Enumerated(EnumType.STRING)
    private ENotificationEntityType notificationType;


}
