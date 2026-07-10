package com.pks.insurance.premium.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import com.pks.insurance.premium.dto.QuoteResponse;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PremiumServiceTest {

    @Mock RestTemplate restTemplate;
    @Spy @InjectMocks PremiumService premiumService;

    @BeforeEach
    void setUp() {
        // Set service URLs via reflection
        org.springframework.test.util.ReflectionTestUtils.setField(premiumService, "userServiceUrl", "http://user-service");
        org.springframework.test.util.ReflectionTestUtils.setField(premiumService, "vehicleServiceUrl", "http://vehicle-service");
    }

    @Test
    void calculateQuote_correctPremiumFormula() {
        // Customer born 30 years ago (approximately)
        LocalDate dob = LocalDate.now().minusYears(30);
        Map<String, Object> userMap = Map.of(
                "ssn", "SSN001",
                "firstName", "Alice",
                "lastName", "Smith",
                "dateOfBirth", dob.toString()
        );
        Map<String, Object> vehicleMap = Map.of(
                "ssn", "SSN001",
                "type", "4",
                "make", "BMW",
                "model", "BMW-200",
                "regNo", "AB1234",
                "policyType", "Collision",
                "policyAmount", 10000,
                "totalAccident", 2
        );

        doReturn(userMap).when(premiumService).fetchUser("SSN001");
        doReturn(vehicleMap).when(premiumService).fetchVehicle("SSN001");

        QuoteResponse quote = premiumService.calculateQuote("SSN001");

        assertThat(quote.getSsn()).isEqualTo("SSN001");
        assertThat(quote.getPolicyAmount()).isEqualTo(10000);
        assertThat(quote.getPremiumAnnually()).isGreaterThan(0);
        // annual = quarterly * 4
        assertThat(quote.getPremiumAnnually()).isEqualTo(quote.getPremiumQuarterly() * 4);
        // annual = monthly * 12
        assertThat(quote.getPremiumAnnually()).isEqualTo(quote.getPremiumMonthly() * 12);
    }

    @Test
    void calculateQuote_missingDob_throwsException() {
        Map<String, Object> userMap = Map.of("ssn", "SSN001", "firstName", "Alice");
        Map<String, Object> vehicleMap = Map.of("ssn", "SSN001", "type", "4", "policyAmount", 5000);

        doReturn(userMap).when(premiumService).fetchUser("SSN001");
        doReturn(vehicleMap).when(premiumService).fetchVehicle("SSN001");

        assertThatThrownBy(() -> premiumService.calculateQuote("SSN001"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("date of birth");
    }

    @Test
    void calculateQuote_missingVehicleType_throwsException() {
        LocalDate dob = LocalDate.now().minusYears(25);
        Map<String, Object> userMap = Map.of("ssn", "SSN001", "dateOfBirth", dob.toString());
        Map<String, Object> vehicleMap = Map.of("ssn", "SSN001", "policyAmount", 5000);

        doReturn(userMap).when(premiumService).fetchUser("SSN001");
        doReturn(vehicleMap).when(premiumService).fetchVehicle("SSN001");

        assertThatThrownBy(() -> premiumService.calculateQuote("SSN001"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Vehicle type is not set");
    }
}
