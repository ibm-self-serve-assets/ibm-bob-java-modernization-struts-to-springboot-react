package com.pks.insurance.vehicle.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Vehicle {

    @Id
    @Column(name = "ssn", length = 20, nullable = false)
    private String ssn;

    @Column(name = "type")
    private String type;

    @Column(name = "model")
    private String model;

    @Column(name = "make")
    private String make;

    @Column(name = "reg_no")
    private String regNo;

    @Column(name = "policy_type")
    private String policyType;

    @Column(name = "policy_amount")
    private Integer policyAmount;

    @Column(name = "mf_year", length = 4)
    private String mfYear;

    @Column(name = "total_accident")
    private Integer totalAccident = 0;

    @Column(name = "quote_date")
    private LocalDate quoteDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
