package com.sweet.home.sweethome.controller;

import com.sweet.home.sweethome.service.CategoryService;
import com.sweet.home.sweethome.service.CleanerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the home/landing page.
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CleanerService cleanerService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("featuredCleaners", cleanerService.findAvailable());
        model.addAttribute("categories", categoryService.findAll());
        return "index";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }
}
