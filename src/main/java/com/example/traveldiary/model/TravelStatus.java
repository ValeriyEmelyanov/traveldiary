package com.example.traveldiary.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TravelStatus {
    PLAN("Plan"), DONE("Done"), CANCELED("Canceled");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
