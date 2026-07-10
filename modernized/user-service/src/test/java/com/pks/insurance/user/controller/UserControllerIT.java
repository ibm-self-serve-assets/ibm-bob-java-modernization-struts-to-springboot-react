package com.pks.insurance.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pks.insurance.user.dto.LoginRequest;
import com.pks.insurance.user.dto.RegisterRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerIT {

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

    static String userJwt;
    static final String TEST_SSN = "IT-SSN-001";

    @Test @Order(1)
    void shouldRegisterNewUser() throws Exception {
        RegisterRequest req = buildRegisterRequest(TEST_SSN);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ssn").value(TEST_SSN))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test @Order(2)
    void shouldRejectDuplicateRegistration() throws Exception {
        RegisterRequest req = buildRegisterRequest(TEST_SSN);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().is4xxClientError());
    }

    @Test @Order(3)
    void shouldLoginAndReturnJwt() throws Exception {
        LoginRequest loginReq = new LoginRequest();
        loginReq.setSsn(TEST_SSN);
        loginReq.setPassword("password123");

        MvcResult result = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        userJwt = objectMapper.readTree(body).get("token").asText();
        assertThat(userJwt).isNotBlank();
    }

    @Test @Order(4)
    void shouldGetProfileWithValidJwt() throws Exception {
        mockMvc.perform(get("/api/users/" + TEST_SSN)
                .header("Authorization", "Bearer " + userJwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ssn").value(TEST_SSN))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test @Order(5)
    void shouldRejectProfileFetchWithoutJwt() throws Exception {
        mockMvc.perform(get("/api/users/" + TEST_SSN))
                .andExpect(status().isForbidden());
    }

    @Test @Order(6)
    void shouldUpdateUserProfile() throws Exception {
        RegisterRequest updateReq = buildRegisterRequest(TEST_SSN);
        updateReq.setCity("New York");

        mockMvc.perform(put("/api/users/" + TEST_SSN)
                .header("Authorization", "Bearer " + userJwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("New York"));
    }

    private RegisterRequest buildRegisterRequest(String ssn) {
        RegisterRequest req = new RegisterRequest();
        req.setSsn(ssn);
        req.setPassword("password123");
        req.setFirstName("Test");
        req.setLastName("User");
        req.setGender("M");
        req.setDateOfBirth("1990-06-15");
        req.setEmail("test@example.com");
        req.setCity("Chicago");
        req.setMobileNo("1234567890");
        return req;
    }
}
