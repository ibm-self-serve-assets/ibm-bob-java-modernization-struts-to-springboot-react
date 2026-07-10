package com.pks.insurance.vehicle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pks.insurance.vehicle.dto.VehicleRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehicleControllerIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("insurance_db")
            .withUsername("insurance_user")
            .withPassword("insurance_pass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    // NOTE: In full integration test, this JWT would come from user-service.
    // Here we use a test JWT pre-signed with the test secret.
    static final String TEST_JWT = "Bearer test-jwt-placeholder";
    static final String TEST_SSN = "SSN-VEH-001";

    @Test @Order(1)
    void shouldRegisterVehicle() throws Exception {
        VehicleRequest req = buildVehicleRequest();

        mockMvc.perform(post("/api/vehicles")
                .header("Authorization", TEST_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ssn").value(TEST_SSN))
                .andExpect(jsonPath("$.make").value("bmw"))
                .andExpect(jsonPath("$.policyAmount").value(10000));
    }

    @Test @Order(2)
    void shouldGetVehicleAfterRegistration() throws Exception {
        mockMvc.perform(get("/api/vehicles/" + TEST_SSN)
                .header("Authorization", TEST_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ssn").value(TEST_SSN))
                .andExpect(jsonPath("$.regNo").value("IT-REG-001"));
    }

    @Test @Order(3)
    void shouldReturn404ForUnknownSsn() throws Exception {
        mockMvc.perform(get("/api/vehicles/UNKNOWN_SSN")
                .header("Authorization", TEST_JWT))
                .andExpect(status().is4xxClientError());
    }

    @Test @Order(4)
    void shouldRejectInvalidVehicleRequest() throws Exception {
        VehicleRequest req = new VehicleRequest();  // missing required fields

        mockMvc.perform(post("/api/vehicles")
                .header("Authorization", TEST_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    private VehicleRequest buildVehicleRequest() {
        VehicleRequest req = new VehicleRequest();
        req.setSsn(TEST_SSN);
        req.setType("4");
        req.setMake("bmw");
        req.setModel("bmw-200");
        req.setRegNo("IT-REG-001");
        req.setPolicyType("Collision");
        req.setPolicyAmount(10000);
        req.setMfYear("2020");
        req.setTotalAccident(0);
        return req;
    }
}
