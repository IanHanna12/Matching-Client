package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private String timeUser;
    private String name;
    private String ID;


    public User() {
    }
    // Constructor with parameters
    public User(String name, String ID, String timeUser) {
        this.name = name;
        this.ID = ID;
        this.timeUser = timeUser;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }



    public String getUsertime() {
        return timeUser;
    }
}