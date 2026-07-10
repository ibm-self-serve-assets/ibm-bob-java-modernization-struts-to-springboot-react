package com.pks.insurance.user.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UserResponse {
    private String ssn;
    private String firstName;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private String mobileNo;
    private String email;
    private String city;
    private String bloodGroup;
    private String drivingLicence;
    private boolean enabled;
}
