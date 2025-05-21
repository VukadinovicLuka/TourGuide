package com.example.turistickivodic.entities;

import javax.validation.constraints.NotEmpty;

public class Activity {
    private int id;

    @NotEmpty
    private String name;

    private int destinationId;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }
}
