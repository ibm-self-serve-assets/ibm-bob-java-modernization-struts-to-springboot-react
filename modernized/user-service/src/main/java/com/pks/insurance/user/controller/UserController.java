package com.pks.insurance.user.controller;

import com.pks.insurance.user.dto.*;
import com.pks.insurance.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(userService.login(req));
    }

    @GetMapping("/{ssn}")
    @PreAuthorize("hasAnyRole('USER','ADMIN') and (authentication.name == #ssn or hasRole('ADMIN'))")
    public ResponseEntity<UserResponse> getUser(@PathVariable String ssn) {
        return ResponseEntity.ok(userService.getUser(ssn));
    }

    @PutMapping("/{ssn}")
    @PreAuthorize("hasAnyRole('USER','ADMIN') and (authentication.name == #ssn or hasRole('ADMIN'))")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String ssn,
                                                    @Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(userService.updateUser(ssn, req));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam(required = false) String ssn) {
        if (ssn != null && !ssn.isBlank()) {
            return ResponseEntity.ok(userService.searchUsers(ssn));
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{ssn}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeUser(@PathVariable String ssn) {
        userService.removeUser(ssn);
        return ResponseEntity.noContent().build();
    }
}
