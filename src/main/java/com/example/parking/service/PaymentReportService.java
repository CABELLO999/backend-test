package com.example.parking.service;

import com.example.parking.model.Payment;
import com.example.parking.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class PaymentReportService {
    @Autowired
    private PaymentRepository paymentRepository;

    public void generateReport(String filename) throws IOException {
        List<Payment> payments = paymentRepository.findAll();
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Residente,Monto,Fecha\n");
            for (Payment p : payments) {
                writer.write(p.getResident().getName() + "," + p.getAmount() + "," + p.getDate() + "\n");
            }
        }
    }
}
