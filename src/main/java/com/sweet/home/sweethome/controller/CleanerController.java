package com.sweet.home.sweethome.controller;

import com.sweet.home.sweethome.dto.ReviewDto;
import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.model.User;
import com.sweet.home.sweethome.repository.CleanerRepository;
import com.sweet.home.sweethome.service.CategoryService;
import com.sweet.home.sweethome.service.CleanerService;
import com.sweet.home.sweethome.service.ReviewService;
import com.sweet.home.sweethome.service.SkillService;
import com.sweet.home.sweethome.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller for cleaner-related pages: browse, profile, dashboard.
 */
@Controller
@RequiredArgsConstructor
public class CleanerController {

    private final CleanerService cleanerService;
    private final CategoryService categoryService;
    private final SkillService skillService;
    private final ReviewService reviewService;
    private final UserService userService;

    // Public: Browse/search cleaners
    @GetMapping("/cleaners")
    public String listCleaners(@RequestParam(required = false) String city,
            @RequestParam(required = false) Long categoryId,
            Model model) {
        List<Cleaner> cleaners;
        if (city != null || categoryId != null) {
            cleaners = cleanerService.searchCleaners(city, categoryId);
        } else {
            cleaners = cleanerService.findAvailable();
        }

        model.addAttribute("cleaners", cleaners);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("searchCity", city);
        model.addAttribute("searchCategoryId", categoryId);
        return "cleaner/list";
    }

    // Public: View cleaner profile
    @GetMapping("/cleaners/{id}")
    public String viewCleanerProfile(@PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        Cleaner cleaner = cleanerService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cleaner not found"));

        model.addAttribute("cleaner", cleaner);
        model.addAttribute("reviews", reviewService.getReviewsForUser(id));
        model.addAttribute("averageRating", reviewService.getAverageRating(id));
        model.addAttribute("reviewCount", reviewService.getReviewCount(id));
        model.addAttribute("reviewDto", new ReviewDto());

        if (userDetails != null) {
            User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
            if (currentUser != null) {
                model.addAttribute("currentUser", currentUser);
                model.addAttribute("hasReviewed", reviewService.hasReviewed(currentUser.getId(), id));
            }
        }

        return "cleaner/profile";
    }

    // Cleaner: Dashboard
    @GetMapping("/cleaner/dashboard")
    public String cleanerDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        model.addAttribute("user", user);
        model.addAttribute("reviews", reviewService.getReviewsForUser(user.getId()));
        model.addAttribute("averageRating", reviewService.getAverageRating(user.getId()));
        model.addAttribute("reviewCount", reviewService.getReviewCount(user.getId()));

        return "cleaner/dashboard";
    }

    // Cleaner: Edit profile form
    @GetMapping("/cleaner/profile/edit")
    public String editProfileForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!(user instanceof Cleaner)) {
            return "redirect:/";
        }

        model.addAttribute("cleaner", user);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("skills", skillService.findAll());

        return "cleaner/edit-profile";
    }

    // Cleaner: Update profile
    @PostMapping("/cleaner/profile/edit")
    public String updateProfile(@AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute Cleaner updatedCleaner,
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false) List<Long> skillIds,
            RedirectAttributes redirectAttributes) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!(user instanceof Cleaner cleaner)) {
            return "redirect:/";
        }

        cleaner.setFirstName(updatedCleaner.getFirstName());
        cleaner.setLastName(updatedCleaner.getLastName());
        cleaner.setPhone(updatedCleaner.getPhone());
        cleaner.setHeadline(updatedCleaner.getHeadline());
        cleaner.setBio(updatedCleaner.getBio());
        cleaner.setCity(updatedCleaner.getCity());
        cleaner.setServiceArea(updatedCleaner.getServiceArea());
        cleaner.setHourlyRate(updatedCleaner.getHourlyRate());
        cleaner.setExperienceYears(updatedCleaner.getExperienceYears());
        cleaner.setAvailable(updatedCleaner.isAvailable());

        if (categoryIds != null) {
            cleaner.setCategories(categoryService.findByIds(categoryIds));
        }
        if (skillIds != null) {
            cleaner.setSkills(skillService.findByIds(skillIds));
        }

        cleanerService.save(cleaner);

        redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        return "redirect:/cleaner/dashboard";
    }
}
