package com.sweet.home.sweethome.service;

import com.sweet.home.sweethome.model.Category;
import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.model.Skill;
import com.sweet.home.sweethome.repository.CleanerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service for cleaner profile management and search.
 */
@Service
@RequiredArgsConstructor
public class CleanerService {

    private final CleanerRepository cleanerRepository;

    @Transactional(readOnly = true)
    public List<Cleaner> findAll() {
        return cleanerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Cleaner> findAvailable() {
        return cleanerRepository.findByAvailableTrue();
    }

    @Transactional(readOnly = true)
    public Optional<Cleaner> findById(Long id) {
        return cleanerRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Cleaner> searchCleaners(String city, Long categoryId) {
        return cleanerRepository.searchCleaners(city, categoryId);
    }

    @Transactional(readOnly = true)
    public List<Cleaner> findByCity(String city) {
        return cleanerRepository.findByCityContainingIgnoreCase(city);
    }

    @Transactional
    public Cleaner save(Cleaner cleaner) {
        return cleanerRepository.save(cleaner);
    }

    @Transactional
    public Cleaner updateProfile(Long cleanerId, String headline, String bio,
            String city, String serviceArea,
            Set<Category> categories, Set<Skill> skills) {
        Cleaner cleaner = cleanerRepository.findById(cleanerId)
                .orElseThrow(() -> new IllegalArgumentException("Cleaner not found"));

        cleaner.setHeadline(headline);
        cleaner.setBio(bio);
        cleaner.setCity(city);
        cleaner.setServiceArea(serviceArea);
        cleaner.setCategories(categories);
        cleaner.setSkills(skills);

        return cleanerRepository.save(cleaner);
    }

    @Transactional
    public void setAvailability(Long cleanerId, boolean available) {
        Cleaner cleaner = cleanerRepository.findById(cleanerId)
                .orElseThrow(() -> new IllegalArgumentException("Cleaner not found"));
        cleaner.setAvailable(available);
        cleanerRepository.save(cleaner);
    }
}
