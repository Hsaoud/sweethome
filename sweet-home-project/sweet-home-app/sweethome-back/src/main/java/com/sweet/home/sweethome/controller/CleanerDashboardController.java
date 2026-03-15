package com.sweet.home.sweethome.controller;

import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.model.User;
import com.sweet.home.sweethome.service.ReviewService;
import com.sweet.home.sweethome.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST Controller for cleaner private dashboard.
 */
@RestController
@RequestMapping("/api/cleaner")
public class CleanerDashboardController {

    private final ReviewService reviewService;
    private final UserService userService;

    public CleanerDashboardController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!(user instanceof Cleaner)) {
            return ResponseEntity.badRequest().body("Not a cleaner account");
        }

        Cleaner cleaner = (Cleaner) user;

        return ResponseEntity.ok(Map.of(
                "user", cleaner, // Includes profile info
                "reviews", reviewService.getReviewsForUser(cleaner.getId()),
                "averageRating", reviewService.getAverageRating(cleaner.getId()),
                "reviewCount", reviewService.getReviewCount(cleaner.getId())));
    }
}
