package com.groupa1.annotation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Annotation {

    @Id
    private String id;

    private String value;
}
