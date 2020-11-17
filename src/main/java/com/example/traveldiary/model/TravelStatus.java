package com.example.traveldiary.model;

public enum TravelStatus {
    PLAN("Plan"), DONE("Done"), CANCELED("Canceled");

    private final String name;

    TravelStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
