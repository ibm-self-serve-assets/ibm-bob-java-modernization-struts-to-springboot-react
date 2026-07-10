package com.pks.insurance.premium.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class QuoteResponse {
    private String ssn;
    private String firstName;
    private String lastName;
    private String vehicleMake;
    private String vehicleModel;
    private String regNo;
    private String policyType;
    private int policyAmount;
    private float riskFactor;
    private int premiumAnnually;
    private int premiumQuarterly;
    private int premiumMonthly;
    private String calculatedOn;
}
