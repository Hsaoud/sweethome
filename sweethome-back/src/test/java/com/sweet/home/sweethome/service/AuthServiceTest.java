package com.sweet.home.sweethome.service;

import com.sweet.home.sweethome.model.Category;
import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.model.Homer;
import com.sweet.home.sweethome.model.Role;
import com.sweet.home.sweethome.model.Skill;
import com.sweet.home.sweethome.repository.CleanerRepository;
import com.sweet.home.sweethome.repository.HomerRepository;
import com.sweet.home.sweethome.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HomerRepository homerRepository;

    @Mock
    private CleanerRepository cleanerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CategoryService categoryService;

    @Mock
    private SkillService skillService;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerHomer_ShouldSaveAndReturnHomer() {
        // Arrange
        Homer homer = new Homer();
        homer.setEmail("homer@test.com");
        homer.setPassword("password123");

        when(userRepository.existsByEmail("homer@test.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded_pass");
        when(homerRepository.save(any(Homer.class))).thenReturn(homer);

        // Act
        Homer savedHomer = authService.registerHomer(homer);

        // Assert
        assertNotNull(savedHomer);
        assertEquals(Role.HOMER, savedHomer.getRole());
        assertTrue(savedHomer.isEnabled());
        assertEquals("encoded_pass", savedHomer.getPassword());

        verify(userRepository).existsByEmail("homer@test.com");
        verify(passwordEncoder).encode(anyString());
        verify(homerRepository).save(any(Homer.class));
    }

    @Test
    void registerHomer_WhenEmailExists_ShouldThrowException() {
        // Arrange
        Homer homer = new Homer();
        homer.setEmail("existing@test.com");

        when(userRepository.existsByEmail("existing@test.com")).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.registerHomer(homer);
        });

        assertEquals("Email already registered", exception.getMessage());
        verify(userRepository).existsByEmail("existing@test.com");
        verifyNoInteractions(passwordEncoder, homerRepository);
    }

    @Test
    void registerCleanerWithIds_ShouldSaveCleanerWithCategoriesAndSkills() {
        // Arrange
        Cleaner cleaner = new Cleaner();
        cleaner.setEmail("cleaner@test.com");
        cleaner.setPassword("password123");

        List<Long> categoryIds = Arrays.asList(1L);
        List<Long> skillIds = Arrays.asList(2L);

        Category category = new Category();
        category.setId(1L);
        category.setName("Cleaning");

        Skill skill = new Skill();
        skill.setId(2L);
        skill.setName("Window washing");

        when(userRepository.existsByEmail("cleaner@test.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded_pass");
        when(categoryService.findByIds(categoryIds)).thenReturn(Arrays.asList(category));
        when(skillService.findByIds(skillIds)).thenReturn(Arrays.asList(skill));
        when(cleanerRepository.save(any(Cleaner.class))).thenReturn(cleaner);

        // Act
        Optional<Cleaner> savedCleanerOpt = authService.registerCleanerWithIds(cleaner, categoryIds, skillIds);

        // Assert
        assertTrue(savedCleanerOpt.isPresent());
        Cleaner savedCleaner = savedCleanerOpt.get();
        assertEquals(Role.CLEANER, savedCleaner.getRole());
        assertTrue(savedCleaner.isEnabled());
        assertEquals("encoded_pass", savedCleaner.getPassword());

        verify(categoryService).findByIds(categoryIds);
        verify(skillService).findByIds(skillIds);
        verify(cleanerRepository).save(cleaner);
    }

    @Test
    void emailExists_ShouldReturnTrue_WhenEmailFound() {
        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);
        boolean exists = authService.emailExists("test@test.com");
        assertTrue(exists);
        verify(userRepository).existsByEmail("test@test.com");
    }
}
