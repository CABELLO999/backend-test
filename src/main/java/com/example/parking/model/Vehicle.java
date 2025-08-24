package com.example.parking.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Vehicle {
    @Id
    private String plate;
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    private long residentAccumulatedMinutes;

    public Vehicle() {}
    public Vehicle(String plate, VehicleType type) {
        this.plate = plate;
        this.type = type;
    }
    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }
    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }
    public long getResidentAccumulatedMinutes() { return residentAccumulatedMinutes; }
    public void setResidentAccumulatedMinutes(long minutes) { this.residentAccumulatedMinutes = minutes; }
}
