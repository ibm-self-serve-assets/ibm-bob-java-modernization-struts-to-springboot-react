package com.pks.insurance.vehicle.service;

import com.pks.insurance.vehicle.domain.Vehicle;
import com.pks.insurance.vehicle.dto.*;
import com.pks.insurance.vehicle.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock VehicleRepository vehicleRepository;
    @InjectMocks VehicleService vehicleService;

    private VehicleRequest buildRequest() {
        VehicleRequest r = new VehicleRequest();
        r.setSsn("SSN001");
        r.setType("4");
        r.setMake("bmw");
        r.setModel("bmw-200");
        r.setRegNo("AB1234");
        r.setPolicyType("Collision");
        r.setPolicyAmount(10000);
        r.setMfYear("2020");
        r.setTotalAccident(0);
        return r;
    }

    @Test
    void registerVehicle_success() {
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(inv -> inv.getArgument(0));

        VehicleResponse response = vehicleService.registerVehicle(buildRequest());

        assertThat(response.getSsn()).isEqualTo("SSN001");
        assertThat(response.getRegNo()).isEqualTo("AB1234");
        assertThat(response.getPolicyAmount()).isEqualTo(10000);
    }

    @Test
    void getVehicle_exists_returnsDetails() {
        Vehicle v = Vehicle.builder().ssn("SSN001").type("4").make("bmw")
                .model("bmw-200").regNo("AB1234").policyType("Collision")
                .policyAmount(10000).mfYear("2020").totalAccident(0).build();

        when(vehicleRepository.findById("SSN001")).thenReturn(Optional.of(v));

        VehicleResponse response = vehicleService.getVehicle("SSN001");

        assertThat(response.getMake()).isEqualTo("bmw");
    }

    @Test
    void getVehicle_notFound_throwsException() {
        when(vehicleRepository.findById("XXX")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> vehicleService.getVehicle("XXX"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Vehicle not found");
    }

    @Test
    void updateVehicle_success_updatesFields() {
        Vehicle existing = Vehicle.builder().ssn("SSN001").policyAmount(5000).build();
        when(vehicleRepository.findById("SSN001")).thenReturn(Optional.of(existing));
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(inv -> inv.getArgument(0));

        VehicleRequest req = buildRequest();
        req.setPolicyAmount(15000);
        VehicleResponse response = vehicleService.updateVehicle("SSN001", req);

        assertThat(response.getPolicyAmount()).isEqualTo(15000);
    }
}
