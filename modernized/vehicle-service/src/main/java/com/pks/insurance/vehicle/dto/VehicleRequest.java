package com.pks.insurance.vehicle.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VehicleRequest {

    @NotBlank
    private String ssn;

    @NotBlank
    private String type;

    @NotBlank
    private String model;

    @NotBlank
    private String make;

    @NotBlank
    private String regNo;

    @NotBlank
    private String policyType;

    @NotNull @Min(1000)
    private Integer policyAmount;

    @NotBlank
    private String mfYear;

    @Min(0) @Max(9)
    private Integer totalAccident = 0;
}
