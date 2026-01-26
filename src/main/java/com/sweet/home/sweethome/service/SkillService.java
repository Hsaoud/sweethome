package com.sweet.home.sweethome.service;

import com.sweet.home.sweethome.model.Skill;
import com.sweet.home.sweethome.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Transactional(readOnly = true)
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Skill> findByIds(List<Long> ids) {
        return skillRepository.findAllById(ids);
    }
}
