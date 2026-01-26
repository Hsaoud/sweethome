package com.sweet.home.sweethome.service;

import com.sweet.home.sweethome.model.Category;
import com.sweet.home.sweethome.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Category> findByIds(List<Long> ids) {
        return categoryRepository.findAllById(ids);
    }
}
