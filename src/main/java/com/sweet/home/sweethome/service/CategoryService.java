package com.sweet.home.sweethome.service;

import com.sweet.home.sweethome.model.Category;
import com.sweet.home.sweethome.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for category management.
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Set<Category> findByIds(List<Long> ids) {
        return categoryRepository.findAllById(ids).stream().collect(Collectors.toSet());
    }

    @Transactional
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
