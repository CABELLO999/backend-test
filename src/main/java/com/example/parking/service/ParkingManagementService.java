package com.example.parking.service;

import com.example.parking.model.Stay;
import com.example.parking.model.Vehicle;
import com.example.parking.model.VehicleType;
import com.example.parking.repository.StayRepository;
import com.example.parking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Servicio principal para la gestión de operaciones del estacionamiento
 *
 * Gestiona: entradas, salidas, registro de vehículos y reinicio mensual
 */
@Service
public class ParkingManagementService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private StayRepository stayRepository;

    // Registra entrada
    public void registerEntry(String plate) {
        Stay stay = new Stay(plate, java.util.Calendar.getInstance());
        stayRepository.save(stay);
    }

    // Registra salida
    public double registerExit(String plate) {
        List<Stay> stays = stayRepository.findByPlate(plate);
        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(plate);
        if (stays.isEmpty() || vehicleOpt.isEmpty()) return 0;
        Stay stay = stays.get(stays.size() - 1);
        stay.setExitTime(java.util.Calendar.getInstance());
        stayRepository.save(stay);
        Vehicle vehicle = vehicleOpt.get();
        long minutes = 0;
        if (stay.getEntryTime() != null && stay.getExitTime() != null) {
            minutes = (stay.getExitTime().getTimeInMillis() - stay.getEntryTime().getTimeInMillis()) / (60 * 1000);
        }
        if (vehicle.getType() == VehicleType.OFICIAL) {
            // Solo guardar estancia
            return 0;
        } else if (vehicle.getType() == VehicleType.RESIDENTE) {
            vehicle.setResidentAccumulatedMinutes(vehicle.getResidentAccumulatedMinutes() + minutes);
            vehicleRepository.save(vehicle);
            return 0;
        } else {
            // No residente: calcular importe
            double price = calculateNonResidentPrice(minutes);
            return price;
        }
    }

    private double calculateNonResidentPrice(long minutes) {
        // Ejemplo: 0.05€/minuto
        return minutes * 0.05;
    }

    // Alta oficial
    public void addOfficial(String plate) {
        vehicleRepository.save(new Vehicle(plate, VehicleType.OFICIAL));
    }

    // Alta residente
    public void addResident(String plate) {
        vehicleRepository.save(new Vehicle(plate, VehicleType.RESIDENTE));
    }

    // Comienza mes
    public void startMonth() {
        // Eliminar estancias de oficiales
        List<Vehicle> officials = vehicleRepository.findAll().stream().filter(v -> v.getType() == VehicleType.OFICIAL).toList();
        for (Vehicle v : officials) {
            stayRepository.deleteByPlate(v.getPlate());
        }
        // Poner a cero tiempo de residentes
        List<Vehicle> residents = vehicleRepository.findAll().stream().filter(v -> v.getType() == VehicleType.RESIDENTE).toList();
        for (Vehicle v : residents) {
            v.setResidentAccumulatedMinutes(0);
            vehicleRepository.save(v);
        }
    }
    public Stay registerEntryFull(String plate) {
        // Validación de formato (ejemplo simple)
        if (plate == null || !plate.matches("^[A-Z]{3}[-][0-9]{4}$")) {
            throw new IllegalArgumentException("Formato de placa inválido");
        }
        // Buscar vehículo
        Vehicle vehicle = vehicleRepository.findById(plate).orElse(null);
        if (vehicle == null) {
            vehicle = new Vehicle(plate, VehicleType.NO_RESIDENTE);
            vehicleRepository.save(vehicle);
        }
        // Verificar que no tenga estancia activa (sin exitTime)
        List<Stay> stays = stayRepository.findByPlate(plate);
        boolean activa = stays.stream().anyMatch(s -> s.getExitTime() == null);
        if (activa) {
            throw new IllegalArgumentException("El vehículo ya está estacionado");
        }
        // Crear nueva estancia
        Stay stay = new Stay();
        stay.setPlate(plate);
        // Usar Calendar para la hora actual
        stay.setEntryTime(java.util.Calendar.getInstance());
        stayRepository.save(stay);
        return stay;
    }
}
