package com.sweet.home.sweethome.service;

import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.repository.CleanerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<com.sweet.home.sweethome.dto.CleanerSearchResponseDto> searchCleaners(Double lat, Double lng,
            Integer surface, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Cleaner> nearby = cleanerRepository.findCleanersWithinRadius(lat, lng);
        return nearby.stream()
                .map(cleaner -> new com.sweet.home.sweethome.dto.CleanerSearchResponseDto(cleaner, surface))
                .collect(Collectors.toList());
    }
}
