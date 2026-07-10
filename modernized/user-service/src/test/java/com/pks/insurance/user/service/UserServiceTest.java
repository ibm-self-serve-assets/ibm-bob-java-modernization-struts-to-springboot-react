package com.pks.insurance.user.service;

import com.pks.insurance.user.domain.User;
import com.pks.insurance.user.dto.*;
import com.pks.insurance.user.repository.UserRepository;
import com.pks.insurance.user.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock UserRepository userRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock JwtUtil jwtUtil;

    @InjectMocks
    UserService userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = User.builder()
                .ssn("SSN123")
                .passwordHash("$2a$10$hash")
                .firstName("Alice")
                .lastName("Smith")
                .gender("F")
                .dateOfBirth(LocalDate.of(1990, 5, 15))
                .email("alice@example.com")
                .enabled(true)
                .roles(Set.of())
                .build();
    }

    // ── Register ────────────────────────────────────────────────

    @Test
    void register_newUser_success() {
        RegisterRequest req = new RegisterRequest();
        req.setSsn("SSN123");
        req.setPassword("pass123");
        req.setFirstName("Alice");
        req.setLastName("Smith");
        req.setGender("F");
        req.setDateOfBirth("1990-05-15");
        req.setEmail("alice@example.com");

        when(userRepository.existsById("SSN123")).thenReturn(false);
        when(passwordEncoder.encode("pass123")).thenReturn("$2a$10$hash");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        UserResponse response = userService.register(req);

        assertThat(response.getSsn()).isEqualTo("SSN123");
        assertThat(response.getFirstName()).isEqualTo("Alice");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_duplicateSsn_throwsException() {
        RegisterRequest req = new RegisterRequest();
        req.setSsn("SSN123");
        when(userRepository.existsById("SSN123")).thenReturn(true);

        assertThatThrownBy(() -> userService.register(req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("SSN already registered");
    }

    // ── Login ───────────────────────────────────────────────────

    @Test
    void login_validCredentials_returnsToken() {
        LoginRequest req = new LoginRequest();
        req.setSsn("SSN123");
        req.setPassword("pass123");

        when(userRepository.findById("SSN123")).thenReturn(Optional.of(sampleUser));
        when(passwordEncoder.matches("pass123", "$2a$10$hash")).thenReturn(true);
        when(jwtUtil.generateToken("SSN123", "ROLE_USER")).thenReturn("mock-jwt-token");

        AuthResponse response = userService.login(req);

        assertThat(response.getToken()).isEqualTo("mock-jwt-token");
        assertThat(response.getSsn()).isEqualTo("SSN123");
    }

    @Test
    void login_invalidPassword_throwsException() {
        LoginRequest req = new LoginRequest();
        req.setSsn("SSN123");
        req.setPassword("wrongpass");

        when(userRepository.findById("SSN123")).thenReturn(Optional.of(sampleUser));
        when(passwordEncoder.matches("wrongpass", "$2a$10$hash")).thenReturn(false);

        assertThatThrownBy(() -> userService.login(req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid credentials");
    }

    @Test
    void login_userNotFound_throwsException() {
        LoginRequest req = new LoginRequest();
        req.setSsn("UNKNOWN");
        req.setPassword("pass");

        when(userRepository.findById("UNKNOWN")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.login(req))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // ── GetUser ─────────────────────────────────────────────────

    @Test
    void getUser_existingUser_returnsProfile() {
        when(userRepository.findById("SSN123")).thenReturn(Optional.of(sampleUser));

        UserResponse response = userService.getUser("SSN123");

        assertThat(response.getSsn()).isEqualTo("SSN123");
        assertThat(response.getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    void getUser_notFound_throwsException() {
        when(userRepository.findById("NOPE")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUser("NOPE"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User not found");
    }

    // ── RemoveUser ──────────────────────────────────────────────

    @Test
    void removeUser_callsRepositoryDeleteById() {
        doNothing().when(userRepository).deleteById("SSN123");

        userService.removeUser("SSN123");

        verify(userRepository).deleteById("SSN123");
    }
}
