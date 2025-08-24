package com.example.parking.controller;

import com.example.parking.service.ParkingManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parking")
public class ParkingManagementController {
    @Autowired
    private ParkingManagementService service;

    @PostMapping("/entrada")
    public ResponseEntity<?> registrarEntrada(@RequestParam String plate) {
        service.registerEntry(plate);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/salida")
    public ResponseEntity<Double> registrarSalida(@RequestParam String plate) {
        double pago = service.registerExit(plate);
        return ResponseEntity.ok(pago);
    }

    @PostMapping("/alta-oficial")
    public ResponseEntity<?> altaOficial(@RequestParam String plate) {
        service.addOfficial(plate);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/alta-residente")
    public ResponseEntity<?> altaResidente(@RequestParam String plate) {
        service.addResident(plate);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/comienza-mes")
    public ResponseEntity<?> comienzaMes() {
        service.startMonth();
        return ResponseEntity.ok().build();
    }
}
