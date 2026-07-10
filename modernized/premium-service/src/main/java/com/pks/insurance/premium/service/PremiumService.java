package com.pks.insurance.premium.service;

import com.pks.insurance.premium.dto.QuoteResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PremiumService {

    private final RestTemplate restTemplate;

    @Value("${services.user-service}")
    private String userServiceUrl;

    @Value("${services.vehicle-service}")
    private String vehicleServiceUrl;

    @CircuitBreaker(name = "user-service", fallbackMethod = "userServiceFallback")
    Map<?, ?> fetchUser(String ssn) {
        return restTemplate.getForObject(userServiceUrl + "/api/users/" + ssn, Map.class);
    }

    @CircuitBreaker(name = "vehicle-service", fallbackMethod = "vehicleServiceFallback")
    Map<?, ?> fetchVehicle(String ssn) {
        return restTemplate.getForObject(vehicleServiceUrl + "/api/vehicles/" + ssn, Map.class);
    }

    @SuppressWarnings("unused")
    private Map<?, ?> userServiceFallback(String ssn, Throwable t) {
        log.error("User service unavailable for SSN {}: {}", ssn, t.getMessage());
        throw new IllegalStateException("User service is currently unavailable. Please try again later.");
    }

    @SuppressWarnings("unused")
    private Map<?, ?> vehicleServiceFallback(String ssn, Throwable t) {
        log.error("Vehicle service unavailable for SSN {}: {}", ssn, t.getMessage());
        throw new IllegalStateException("Vehicle service is currently unavailable. Please try again later.");
    }

    /**
     * Core premium calculation logic — preserved from legacy PremiumManagerImpl.
     *
     * riskFactor = (age / 210) + (totalAccident / 10)
     * premiumAnnually = policyAmount * riskFactor
     */
    public QuoteResponse calculateQuote(String ssn) {
        log.info("Calculating premium quote for SSN: {}", ssn);

        Map<?, ?> user = fetchUser(ssn);
        Map<?, ?> vehicle = fetchVehicle(ssn);

        String dobStr = (String) user.get("dateOfBirth");
        if (dobStr == null) {
            throw new IllegalArgumentException("Customer date of birth is not set for SSN: " + ssn);
        }

        String vehicleType = (String) vehicle.get("type");
        if (vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type is not set for SSN: " + ssn);
        }

        LocalDate dob = LocalDate.parse(dobStr);
        long ageInDays = java.time.temporal.ChronoUnit.DAYS.between(dob, LocalDate.now());
        float age = ageInDays / 365.0f;

        int totalAccident = vehicle.get("totalAccident") instanceof Number n ? n.intValue() : 0;
        int policyAmount = vehicle.get("policyAmount") instanceof Number n ? n.intValue() : 0;

        float riskFactor = (age / 210.0f) + ((float) totalAccident / 10.0f);
        int premiumAnnually = (int) (policyAmount * riskFactor);
        int premiumQuarterly = premiumAnnually / 4;
        int premiumMonthly = premiumAnnually / 12;

        return QuoteResponse.builder()
                .ssn(ssn)
                .firstName((String) user.get("firstName"))
                .lastName((String) user.get("lastName"))
                .vehicleMake((String) vehicle.get("make"))
                .vehicleModel((String) vehicle.get("model"))
                .regNo((String) vehicle.get("regNo"))
                .policyType((String) vehicle.get("policyType"))
                .policyAmount(policyAmount)
                .riskFactor(riskFactor)
                .premiumAnnually(premiumAnnually)
                .premiumQuarterly(premiumQuarterly)
                .premiumMonthly(premiumMonthly)
                .calculatedOn(LocalDate.now().toString())
                .build();
    }
}
