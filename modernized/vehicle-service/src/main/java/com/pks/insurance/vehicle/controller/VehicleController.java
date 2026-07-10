package com.pks.insurance.vehicle.controller;

import com.pks.insurance.vehicle.dto.*;
import com.pks.insurance.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<VehicleResponse> registerVehicle(@Valid @RequestBody VehicleRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.registerVehicle(req));
    }

    @GetMapping("/{ssn}")
    @PreAuthorize("hasAnyRole('USER','ADMIN') and (authentication.name == #ssn or hasRole('ADMIN'))")
    public ResponseEntity<VehicleResponse> getVehicle(@PathVariable String ssn) {
        return ResponseEntity.ok(vehicleService.getVehicle(ssn));
    }

    @PutMapping("/{ssn}")
    @PreAuthorize("hasAnyRole('USER','ADMIN') and (authentication.name == #ssn or hasRole('ADMIN'))")
    public ResponseEntity<VehicleResponse> updateVehicle(@PathVariable String ssn,
                                                          @Valid @RequestBody VehicleRequest req) {
        return ResponseEntity.ok(vehicleService.updateVehicle(ssn, req));
    }
}
