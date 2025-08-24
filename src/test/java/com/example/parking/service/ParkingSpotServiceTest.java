package com.example.parking.service;

import com.example.parking.model.ParkingSpot;
import com.example.parking.repository.ParkingSpotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class ParkingSpotServiceTest {
    @Mock
    private ParkingSpotRepository repository;
    @InjectMocks
    private ParkingSpotService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateParkingSpot_DuplicateCode_ThrowsException() {
        ParkingSpot spot = new ParkingSpot("A1", false);
        when(repository.existsByCode("A1")).thenReturn(true);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.create(spot));
    }

    @Test
    void testFindAll_ReturnsList() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertTrue(service.findAll().isEmpty());
    }

    @Test
    void testFindById_ReturnsOptional() {
        ParkingSpot spot = new ParkingSpot("A2", false);
        when(repository.findById(1L)).thenReturn(Optional.of(spot));
        Assertions.assertTrue(service.findById(1L).isPresent());
    }
}
