package com.sweet.home.sweethome.controller;

import com.sweet.home.sweethome.service.CategoryService;
import com.sweet.home.sweethome.service.CleanerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST Controller for the home/landing page data.
 */
@RestController
@RequestMapping("/api/public")
public class HomeController {

    private final CleanerService cleanerService;
    private final CategoryService categoryService;

    public HomeController(CleanerService cleanerService, CategoryService categoryService) {
        this.cleanerService = cleanerService;
        this.categoryService = categoryService;
    }

    @GetMapping("/home-data")
    public ResponseEntity<?> getHomeData() {
        return ResponseEntity.ok(Map.of(
                "featuredCleaners", cleanerService.findAvailable(),
                "categories", categoryService.findAll()));
    }
}
