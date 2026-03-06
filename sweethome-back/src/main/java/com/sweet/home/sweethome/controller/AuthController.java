package com.sweet.home.sweethome.controller;

import com.sweet.home.sweethome.dto.CleanerRegistrationDto;
import com.sweet.home.sweethome.dto.HomerRegistrationDto;
import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.model.Homer;
import com.sweet.home.sweethome.model.User;
import com.sweet.home.sweethome.service.AuthService;
import com.sweet.home.sweethome.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for authentication via REST API.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager,
            UserService userService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("username");
        String password = loginRequest.get("password");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.findByEmail(email).orElseThrow();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/homer")
    public ResponseEntity<?> registerHomer(@Valid @RequestBody HomerRegistrationDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Passwords do not match"));
        }

        if (authService.emailExists(dto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Email already registered"));
        }

        Homer homer = new Homer();
        homer.setEmail(dto.getEmail());
        homer.setPassword(dto.getPassword());
        homer.setFirstName(dto.getFirstName());
        homer.setLastName(dto.getLastName());
        homer.setPhone(dto.getPhone());
        homer.setAddress(dto.getAddress());
        homer.setCity(dto.getCity());
        homer.setPostalCode(dto.getPostalCode());

        Homer savedHomer = authService.registerHomer(homer);
        return ResponseEntity.ok(savedHomer);
    }

    @PostMapping("/register/cleaner")
    public ResponseEntity<?> registerCleaner(@Valid @RequestBody CleanerRegistrationDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Passwords do not match"));
        }

        if (authService.emailExists(dto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Email already registered"));
        }

        Cleaner cleaner = new Cleaner();
        cleaner.setEmail(dto.getEmail());
        cleaner.setPassword(dto.getPassword());
        cleaner.setFirstName(dto.getFirstName());
        cleaner.setLastName(dto.getLastName());
        cleaner.setPhone(dto.getPhone());
        cleaner.setHeadline(dto.getHeadline());
        cleaner.setBio(dto.getBio());
        cleaner.setCity(dto.getCity());
        cleaner.setPricePerSqm(dto.getPricePerSqm());
        cleaner.setActionRadiusKm(dto.getActionRadiusKm() != null ? dto.getActionRadiusKm() : 30);
        cleaner.setExperienceYears(dto.getExperienceYears());

        return authService.registerCleanerWithIds(cleaner, dto.getCategoryIds(), dto.getSkillIds())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
