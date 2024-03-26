package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty
    private String name;
    @JsonProperty
    private String ID;

    // No-argument constructor
    public User() {}

    // Constructor with parameters
    public User(String name, String ID) {
        this.name = name;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }
}