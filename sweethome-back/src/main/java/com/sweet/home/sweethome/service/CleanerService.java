package com.sweet.home.sweethome.service;

import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.repository.CleanerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CleanerService {

    private final CleanerRepository cleanerRepository;

    public CleanerService(CleanerRepository cleanerRepository) {
        this.cleanerRepository = cleanerRepository;
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
}
