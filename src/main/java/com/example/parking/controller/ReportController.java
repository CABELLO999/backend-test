package com.example.parking.controller;

import com.example.parking.service.PaymentReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private PaymentReportService reportService;

    @PostMapping("/payments")
    public ResponseEntity<String> generatePaymentsReport(@RequestParam String filename) {
        try {
            reportService.generateReport(filename);
            return ResponseEntity.ok("Informe generado: " + filename);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al generar el informe");
        }
    }
}
