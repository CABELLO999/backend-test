package com.example.parking.service;

import com.example.parking.model.ParkingSpot;
import com.example.parking.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpotService {
    @Autowired
    private ParkingSpotRepository repository;

    public List<ParkingSpot> findAll() {
        return repository.findAll();
    }

    public Optional<ParkingSpot> findById(Long id) {
        return repository.findById(id);
    }

    public ParkingSpot create(ParkingSpot spot) {
        if (repository.existsByCode(spot.getCode())) {
            throw new IllegalArgumentException("El código de plaza ya existe");
        }
        return repository.save(spot);
    }

    public ParkingSpot update(Long id, ParkingSpot spot) {
        ParkingSpot existing = repository.findById(id).orElseThrow();
        existing.setCode(spot.getCode());
        existing.setOccupied(spot.isOccupied());
        return repository.save(existing);
    }
}
