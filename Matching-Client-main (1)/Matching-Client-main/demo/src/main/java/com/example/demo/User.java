package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @JsonProperty
    private String name;
    @JsonProperty
    private String ID;

   public User() {}
    public User(String s, String string) {}

    // Constructor with parameters
    public User(String name, String ID, String time) {
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