package com.example.parking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
public class Stay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String plate;
    private Calendar entryTime;
    private Calendar exitTime;

    public Stay() {}
    public Stay(String plate, Calendar entryTime) {
        this.plate = plate;
        this.entryTime = entryTime;
    }
    public Long getId() { return id; }
    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }
    public Calendar getEntryTime() { return entryTime; }
    public void setEntryTime(Calendar entryTime) { this.entryTime = entryTime; }
    public Calendar getExitTime() { return exitTime; }
    public void setExitTime(Calendar exitTime) { this.exitTime = exitTime; }
}
