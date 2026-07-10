package com.pks.insurance.vehicle.service;

import com.pks.insurance.vehicle.domain.Vehicle;
import com.pks.insurance.vehicle.dto.*;
import com.pks.insurance.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Transactional
    public VehicleResponse registerVehicle(VehicleRequest req) {
        Vehicle vehicle = Vehicle.builder()
                .ssn(req.getSsn())
                .type(req.getType())
                .model(req.getModel())
                .make(req.getMake())
                .regNo(req.getRegNo())
                .policyType(req.getPolicyType())
                .policyAmount(req.getPolicyAmount())
                .mfYear(req.getMfYear())
                .totalAccident(req.getTotalAccident())
                .quoteDate(LocalDate.now())
                .build();
        vehicleRepository.save(vehicle);
        log.info("Vehicle registered for SSN: {}", req.getSsn());
        return toResponse(vehicle);
    }

    public VehicleResponse getVehicle(String ssn) {
        return vehicleRepository.findById(ssn)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found for SSN: " + ssn));
    }

    @Transactional
    public VehicleResponse updateVehicle(String ssn, VehicleRequest req) {
        Vehicle vehicle = vehicleRepository.findById(ssn)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found for SSN: " + ssn));
        vehicle.setType(req.getType());
        vehicle.setModel(req.getModel());
        vehicle.setMake(req.getMake());
        vehicle.setRegNo(req.getRegNo());
        vehicle.setPolicyType(req.getPolicyType());
        vehicle.setPolicyAmount(req.getPolicyAmount());
        vehicle.setMfYear(req.getMfYear());
        vehicle.setTotalAccident(req.getTotalAccident());
        vehicleRepository.save(vehicle);
        return toResponse(vehicle);
    }

    private VehicleResponse toResponse(Vehicle v) {
        return VehicleResponse.builder()
                .ssn(v.getSsn())
                .type(v.getType())
                .model(v.getModel())
                .make(v.getMake())
                .regNo(v.getRegNo())
                .policyType(v.getPolicyType())
                .policyAmount(v.getPolicyAmount())
                .mfYear(v.getMfYear())
                .totalAccident(v.getTotalAccident())
                .quoteDate(v.getQuoteDate() != null ? v.getQuoteDate().toString() : null)
                .build();
    }
}
