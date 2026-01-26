package com.sweet.home.sweethome.service;

import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.model.Homer;
import com.sweet.home.sweethome.model.Role;
import com.sweet.home.sweethome.model.User;
import com.sweet.home.sweethome.repository.CleanerRepository;
import com.sweet.home.sweethome.repository.HomerRepository;
import com.sweet.home.sweethome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for user authentication and registration.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final HomerRepository homerRepository;
    private final CleanerRepository cleanerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Homer registerHomer(Homer homer) {
        if (userRepository.existsByEmail(homer.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        homer.setPassword(passwordEncoder.encode(homer.getPassword()));
        homer.setRole(Role.HOMER);
        homer.setEnabled(true);

        return homerRepository.save(homer);
    }

    @Transactional
    public Cleaner registerCleaner(Cleaner cleaner) {
        if (userRepository.existsByEmail(cleaner.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        cleaner.setPassword(passwordEncoder.encode(cleaner.getPassword()));
        cleaner.setRole(Role.CLEANER);
        cleaner.setEnabled(true);

        return cleanerRepository.save(cleaner);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
