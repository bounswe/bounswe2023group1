package com.groupa1.annotation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Annotation {

    @Id
    private String id;

    @Column(length = 4096)
    private String value;
}
