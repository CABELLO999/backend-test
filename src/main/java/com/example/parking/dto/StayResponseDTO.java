package com.example.parking.dto;

import java.util.Calendar;

public class StayResponseDTO {
    private Long id;
    private String plate;
    private Calendar entryTime;

    public StayResponseDTO(Long id, String plate, Calendar entryTime) {
        this.id = id;
        this.plate = plate;
        this.entryTime = entryTime;
    }
    public Long getId() { return id; }
    public String getPlate() { return plate; }
    public Calendar getEntryTime() { return entryTime; }
}
