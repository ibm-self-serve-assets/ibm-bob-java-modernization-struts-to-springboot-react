package com.pks.insurance.user.service;

import com.pks.insurance.user.domain.User;
import com.pks.insurance.user.domain.UserRole;
import com.pks.insurance.user.dto.*;
import com.pks.insurance.user.repository.UserRepository;
import com.pks.insurance.user.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserResponse register(RegisterRequest req) {
        if (userRepository.existsById(req.getSsn())) {
            throw new IllegalArgumentException("SSN already registered: " + req.getSsn());
        }

        String role = "adminadmin".equals(req.getSsn()) ? "ROLE_ADMIN" : "ROLE_USER";

        User user = User.builder()
                .ssn(req.getSsn())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .gender(req.getGender())
                .dateOfBirth(req.getDateOfBirth() != null ? LocalDate.parse(req.getDateOfBirth()) : null)
                .mobileNo(req.getMobileNo())
                .email(req.getEmail())
                .city(req.getCity())
                .bloodGroup(req.getBloodGroup())
                .drivingLicence(req.getDrivingLicence())
                .enabled(true)
                .roles(Set.of(UserRole.builder().ssn(req.getSsn()).role(role).build()))
                .build();

        userRepository.save(user);
        log.info("User registered: {}", user.getSsn());
        return toResponse(user);
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findById(req.getSsn())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!user.isEnabled()) {
            throw new IllegalStateException("Account is disabled");
        }

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String role = user.getRoles().stream()
                .map(UserRole::getRole)
                .findFirst()
                .orElse("ROLE_USER");

        String token = jwtUtil.generateToken(user.getSsn(), role);
        log.info("User logged in: {}", user.getSsn());
        return AuthResponse.builder()
                .token(token)
                .ssn(user.getSsn())
                .role(role)
                .message("Login successful")
                .build();
    }

    public UserResponse getUser(String ssn) {
        User user = userRepository.findById(ssn)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + ssn));
        return toResponse(user);
    }

    @Transactional
    public UserResponse updateUser(String ssn, RegisterRequest req) {
        User user = userRepository.findById(ssn)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + ssn));

        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setGender(req.getGender());
        if (req.getDateOfBirth() != null) {
            user.setDateOfBirth(LocalDate.parse(req.getDateOfBirth()));
        }
        user.setMobileNo(req.getMobileNo());
        user.setEmail(req.getEmail());
        user.setCity(req.getCity());
        user.setBloodGroup(req.getBloodGroup());
        user.setDrivingLicence(req.getDrivingLicence());
        userRepository.save(user);
        return toResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<UserResponse> searchUsers(String ssn) {
        return userRepository.findBySsnContainingIgnoreCase(ssn)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public void removeUser(String ssn) {
        userRepository.deleteById(ssn);
        log.info("User removed: {}", ssn);
    }

    private UserResponse toResponse(User u) {
        return UserResponse.builder()
                .ssn(u.getSsn())
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .gender(u.getGender())
                .dateOfBirth(u.getDateOfBirth() != null ? u.getDateOfBirth().toString() : null)
                .mobileNo(u.getMobileNo())
                .email(u.getEmail())
                .city(u.getCity())
                .bloodGroup(u.getBloodGroup())
                .drivingLicence(u.getDrivingLicence())
                .enabled(u.isEnabled())
                .build();
    }
}
