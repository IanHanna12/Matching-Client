package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private String time;
    private String name;
    private String ID;


    public User() {
    }
    // Constructor with parameters
    public User(String name, String ID, String time) {
        this.name = name;
        this.ID = ID;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }



    public String getUsertime() {
       return time;
    }
}