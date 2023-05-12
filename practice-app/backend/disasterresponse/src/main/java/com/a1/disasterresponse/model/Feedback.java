package com.a1.disasterresponse.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String message;

    // Constructors, getters, and setters

    public Feedback() {
    }

    public Feedback(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public String getMessage() {
        return message;
    }
    public String getName() {
        return name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    
}
