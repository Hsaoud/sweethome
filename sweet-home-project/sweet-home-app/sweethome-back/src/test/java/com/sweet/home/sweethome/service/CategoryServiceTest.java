package com.sweet.home.sweethome.service;

import com.sweet.home.sweethome.model.Category;
import com.sweet.home.sweethome.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void findAll_ShouldReturnListOfCategories() {
        // Arrange
        Category cat1 = new Category();
        cat1.setId(1L);
        cat1.setName("Plumbing");

        Category cat2 = new Category();
        cat2.setId(2L);
        cat2.setName("Cleaning");

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(cat1, cat2));

        // Act
        List<Category> result = categoryService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Plumbing", result.get(0).getName());
        assertEquals("Cleaning", result.get(1).getName());
        verify(categoryRepository).findAll();
    }

    @Test
    void findByIds_ShouldReturnListOfCategories() {
        // Arrange
        Category cat1 = new Category();
        cat1.setId(1L);
        cat1.setName("Cooking");

        List<Long> ids = Arrays.asList(1L);
        when(categoryRepository.findAllById(ids)).thenReturn(Arrays.asList(cat1));

        // Act
        List<Category> result = categoryService.findByIds(ids);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Cooking", result.get(0).getName());
        verify(categoryRepository).findAllById(ids);
    }
}
