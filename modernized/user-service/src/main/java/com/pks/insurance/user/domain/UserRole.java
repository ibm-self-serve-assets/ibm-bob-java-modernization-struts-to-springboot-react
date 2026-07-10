package com.pks.insurance.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_roles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ssn", nullable = false)
    private String ssn;

    @Column(name = "role", nullable = false)
    private String role;
}
