package com.example.parking.service;

import com.example.parking.model.Vehicle;
import com.example.parking.model.VehicleType;
import com.example.parking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class ResidentPaymentReportService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public void generateResidentPaymentsReport(String filename) throws IOException {
        List<Vehicle> residents = vehicleRepository.findAll().stream()
                .filter(v -> v.getType() == VehicleType.RESIDENTE)
                .toList();
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Placa,Minutos estacionados,Importe a pagar\n");
            for (Vehicle v : residents) {
                double amount = calculateAmount(v.getResidentAccumulatedMinutes());
                writer.write(v.getPlate() + "," + v.getResidentAccumulatedMinutes() + "," + amount + "\n");
            }
        }
    }

    private double calculateAmount(long minutes) {
        // Ejemplo: 0.01€/minuto
        return minutes * 0.01;
    }
}
