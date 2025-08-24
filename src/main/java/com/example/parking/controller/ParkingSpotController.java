package com.example.parking.controller;

import com.example.parking.model.ParkingSpot;
import com.example.parking.service.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parkingspots")
public class ParkingSpotController {
    @Autowired
    private ParkingSpotService service;

    @GetMapping
    public List<ParkingSpot> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpot> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ParkingSpot> create(@RequestBody ParkingSpot spot) {
        try {
            return ResponseEntity.ok(service.create(spot));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpot> update(@PathVariable Long id, @RequestBody ParkingSpot spot) {
        try {
            return ResponseEntity.ok(service.update(id, spot));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
