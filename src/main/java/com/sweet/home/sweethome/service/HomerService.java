package com.sweet.home.sweethome.service;

import com.sweet.home.sweethome.model.Homer;
import com.sweet.home.sweethome.repository.HomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for homer profile management.
 */
@Service
@RequiredArgsConstructor
public class HomerService {

    private final HomerRepository homerRepository;

    @Transactional(readOnly = true)
    public List<Homer> findAll() {
        return homerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Homer> findById(Long id) {
        return homerRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Homer> findByCity(String city) {
        return homerRepository.findByCityContainingIgnoreCase(city);
    }

    @Transactional
    public Homer save(Homer homer) {
        return homerRepository.save(homer);
    }

    @Transactional
    public Homer updateProfile(Long homerId, String address, String city,
            String postalCode, String additionalInfo) {
        Homer homer = homerRepository.findById(homerId)
                .orElseThrow(() -> new IllegalArgumentException("Homer not found"));

        homer.setAddress(address);
        homer.setCity(city);
        homer.setPostalCode(postalCode);
        homer.setAdditionalInfo(additionalInfo);

        return homerRepository.save(homer);
    }
}
