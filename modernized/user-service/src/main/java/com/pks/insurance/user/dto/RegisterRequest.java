package com.pks.insurance.user.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank @Size(min = 6, max = 20)
    private String ssn;

    @NotBlank @Size(min = 6, max = 30)
    private String password;

    private String firstName;
    private String lastName;

    @NotNull
    private String gender;  // M or F

    @NotBlank
    private String dateOfBirth;  // yyyy-MM-dd

    private String mobileNo;

    @NotBlank @Email
    private String email;

    private String city;
    private String bloodGroup;
    private String drivingLicence;
}
