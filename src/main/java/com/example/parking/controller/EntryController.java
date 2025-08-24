package com.example.parking.controller;

import com.example.parking.dto.EntryRequestDTO;
import com.example.parking.dto.StayResponseDTO;
import com.example.parking.model.Stay;
import com.example.parking.service.ParkingManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/parking/entries")
public class EntryController {
    private static final Logger logger = LoggerFactory.getLogger(EntryController.class);
    @Autowired
    private ParkingManagementService service;

    @PostMapping
    public ResponseEntity<?> registerEntry(@Valid @RequestBody EntryRequestDTO request) {
        String plate = request.getPlate();
        // Validación de formato de placa (ejemplo simple)
        if (plate == null || !plate.matches("^[A-Z]{3}[-][0-9]{4}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato de placa inválido");
        }
        // TODO: Verificar autenticación/autorización
        logger.info("Intentando registrar entrada para placa: {}", plate);
        try {
            Stay stay = service.registerEntryFull(plate);
            StayResponseDTO dto = new StayResponseDTO(stay.getId(), stay.getPlate(), stay.getEntryTime());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Location", "/api/parking/entries/" + dto.getId())
                    .body(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al registrar entrada", e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Error de base de datos");
        }
    }
}
