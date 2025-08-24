package com.example.parking.controller;

import com.example.parking.model.VehicleType;
import com.example.parking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ResidentReportRestController {
    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping("/resident-payments-table")
    public List<Map<String, Object>> getResidentPaymentsTable() {
        return vehicleRepository.findAll().stream()
                .filter(v -> v.getType() == VehicleType.RESIDENTE)
                .map(v -> {
                    Map<String, Object> map = Map.of(
                            "plate", v.getPlate(),
                            "minutes", v.getResidentAccumulatedMinutes(),
                            "amount", v.getResidentAccumulatedMinutes() * 0.01
                    );
                    return map;
                })
                .collect(Collectors.toList());
    }
}
