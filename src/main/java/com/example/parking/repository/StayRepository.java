package com.example.parking.repository;

import com.example.parking.model.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StayRepository extends JpaRepository<Stay, Long> {
    List<Stay> findByPlate(String plate);
    void deleteByPlate(String plate);
}
