package com.sweet.home.sweethome.service;

import com.sweet.home.sweethome.model.Skill;
import com.sweet.home.sweethome.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for skill management.
 */
@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    @Transactional(readOnly = true)
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Skill> findById(Long id) {
        return skillRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Set<Skill> findByIds(List<Long> ids) {
        return skillRepository.findAllById(ids).stream().collect(Collectors.toSet());
    }

    @Transactional
    public Skill save(Skill skill) {
        return skillRepository.save(skill);
    }
}
