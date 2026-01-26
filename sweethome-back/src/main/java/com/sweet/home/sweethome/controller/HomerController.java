package com.sweet.home.sweethome.controller;

import com.sweet.home.sweethome.model.Homer;
import com.sweet.home.sweethome.model.User;
import com.sweet.home.sweethome.service.HomerService;
import com.sweet.home.sweethome.service.ReviewService;
import com.sweet.home.sweethome.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST Controller for homer-related pages: profile, dashboard.
 */
@RestController
@RequestMapping("/api/homer")
public class HomerController {

        private final HomerService homerService;
        private final ReviewService reviewService;
        private final UserService userService;

        public HomerController(HomerService homerService, ReviewService reviewService, UserService userService) {
                this.homerService = homerService;
                this.reviewService = reviewService;
                this.userService = userService;
        }

        // Public: View homer profile
        @GetMapping("/{id}")
        public ResponseEntity<?> viewHomerProfile(@PathVariable Long id) {
                Homer homer = homerService.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Homer not found"));

                return ResponseEntity.ok(Map.of(
                                "homer", homer,
                                "reviews", reviewService.getReviewsForUser(id),
                                "averageRating", reviewService.getAverageRating(id),
                                "reviewCount", reviewService.getReviewCount(id)));
        }

        // Homer: Dashboard Data
        @GetMapping("/dashboard")
        public ResponseEntity<?> homerDashboard(@AuthenticationPrincipal UserDetails userDetails) {
                User user = userService.findByEmail(userDetails.getUsername())
                                .orElseThrow(() -> new IllegalArgumentException("User not found"));

                return ResponseEntity.ok(Map.of(
                                "user", user,
                                "reviewsReceived", reviewService.getReviewsForUser(user.getId()),
                                "reviewsGiven", reviewService.getReviewsByReviewer(user),
                                "averageRating", reviewService.getAverageRating(user.getId())));
        }

        // Homer: Update profile
        @PutMapping("/profile")
        public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                        @RequestBody Homer updatedHomer) {
                User user = userService.findByEmail(userDetails.getUsername())
                                .orElseThrow(() -> new IllegalArgumentException("User not found"));

                if (!(user instanceof Homer)) {
                        return ResponseEntity.badRequest().body("Not a homer account");
                }

                Homer saved = homerService.updateProfile(
                                user.getId(),
                                updatedHomer.getAddress(),
                                updatedHomer.getCity(),
                                updatedHomer.getPostalCode(),
                                updatedHomer.getAdditionalInfo());

                return ResponseEntity.ok(saved);
        }
}
