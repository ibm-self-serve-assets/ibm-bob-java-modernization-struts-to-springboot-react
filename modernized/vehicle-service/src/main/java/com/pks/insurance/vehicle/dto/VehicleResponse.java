package com.pks.insurance.vehicle.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class VehicleResponse {
    private String ssn;
    private String type;
    private String model;
    private String make;
    private String regNo;
    private String policyType;
    private Integer policyAmount;
    private String mfYear;
    private Integer totalAccident;
    private String quoteDate;
}
