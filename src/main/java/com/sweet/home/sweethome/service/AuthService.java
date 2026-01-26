package com.sweet.home.sweethome.service;

import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.model.Homer;
import com.sweet.home.sweethome.model.Role;
import com.sweet.home.sweethome.repository.CleanerRepository;
import com.sweet.home.sweethome.repository.HomerRepository;
import com.sweet.home.sweethome.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final HomerRepository homerRepository;
    private final CleanerRepository cleanerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryService categoryService;
    private final SkillService skillService;

    public AuthService(UserRepository userRepository, HomerRepository homerRepository,
            CleanerRepository cleanerRepository, PasswordEncoder passwordEncoder,
            CategoryService categoryService, SkillService skillService) {
        this.userRepository = userRepository;
        this.homerRepository = homerRepository;
        this.cleanerRepository = cleanerRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryService = categoryService;
        this.skillService = skillService;
    }

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

    @Transactional
    public Optional<Cleaner> registerCleanerWithIds(Cleaner cleaner, List<Long> categoryIds, List<Long> skillIds) {
        if (userRepository.existsByEmail(cleaner.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        cleaner.setPassword(passwordEncoder.encode(cleaner.getPassword()));
        cleaner.setRole(Role.CLEANER);
        cleaner.setEnabled(true);

        if (categoryIds != null && !categoryIds.isEmpty()) {
            cleaner.setCategories(new java.util.HashSet<>(categoryService.findByIds(categoryIds)));
        }
        if (skillIds != null && !skillIds.isEmpty()) {
            cleaner.setSkills(new java.util.HashSet<>(skillService.findByIds(skillIds)));
        }

        return Optional.of(cleanerRepository.save(cleaner));
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
