package com.sweet.home.sweethome.controller;

import com.sweet.home.sweethome.dto.ReviewDto;
import com.sweet.home.sweethome.model.User;
import com.sweet.home.sweethome.service.ReviewService;
import com.sweet.home.sweethome.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST Controller for review operations.
 */
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewDto reviewDto,
            BindingResult result,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid review data"));
        }

        User reviewer = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        try {
            var review = reviewService.createReview(
                    reviewer.getId(),
                    reviewDto.getRevieweeId(),
                    reviewDto.getRating(),
                    reviewDto.getComment());
            return ResponseEntity.ok(review);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
