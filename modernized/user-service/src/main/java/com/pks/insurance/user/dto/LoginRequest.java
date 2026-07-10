package com.pks.insurance.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    private String ssn;

    @NotBlank
    private String password;
}
