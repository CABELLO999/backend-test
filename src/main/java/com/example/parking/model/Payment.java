package com.example.parking.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "resident_id")
    private Resident resident;

    public Payment() {}
    public Payment(double amount, LocalDate date, Resident resident) {
        this.amount = amount;
        this.date = date;
        this.resident = resident;
    }
    public Long getId() { return id; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public Resident getResident() { return resident; }
    public void setResident(Resident resident) { this.resident = resident; }
}
