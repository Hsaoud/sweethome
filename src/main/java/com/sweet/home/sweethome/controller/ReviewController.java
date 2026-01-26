package com.sweet.home.sweethome.controller;

import com.sweet.home.sweethome.dto.ReviewDto;
import com.sweet.home.sweethome.model.User;
import com.sweet.home.sweethome.service.ReviewService;
import com.sweet.home.sweethome.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for review operations.
 */
@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @PostMapping
    public String createReview(@Valid @ModelAttribute("reviewDto") ReviewDto reviewDto,
            BindingResult result,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Please provide a valid rating and comment");
            return "redirect:/cleaners/" + reviewDto.getRevieweeId();
        }

        User reviewer = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        try {
            reviewService.createReview(
                    reviewer.getId(),
                    reviewDto.getRevieweeId(),
                    reviewDto.getRating(),
                    reviewDto.getComment());
            redirectAttributes.addFlashAttribute("success", "Review submitted successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/cleaners/" + reviewDto.getRevieweeId();
    }
}
