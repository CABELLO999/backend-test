package com.example.parking.controller;

import com.example.parking.model.ParkingSpot;
import com.example.parking.service.ParkingSpotService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(ParkingSpotController.class)
public class ParkingSpotControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ParkingSpotService service;

    @Test
    void testGetAll() throws Exception {
        when(service.findAll()).thenReturn(Arrays.asList(new ParkingSpot("A1", false)));
        mockMvc.perform(get("/api/parkingspots"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/parkingspots/1"))
                .andExpect(status().isNotFound());
    }
}
