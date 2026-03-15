package com.sweet.home.sweethome.controller;

import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.model.User;
import com.sweet.home.sweethome.service.CleanerService;
import com.sweet.home.sweethome.service.ReviewService;
import com.sweet.home.sweethome.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * REST Controller for cleaner-related operations.
 */
@RestController
@RequestMapping("/api/cleaners")
public class CleanerController {

    private final CleanerService cleanerService;
    private final ReviewService reviewService;
    private final UserService userService;

    public CleanerController(CleanerService cleanerService, ReviewService reviewService, UserService userService) {
        this.cleanerService = cleanerService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    // Public: Browse/search cleaners
    @GetMapping
    public ResponseEntity<?> listCleaners(@RequestParam(required = false) String city,
            @RequestParam(required = false) Long categoryId) {
        List<Cleaner> cleaners;
        if (city != null || categoryId != null) {
            cleaners = cleanerService.searchCleaners(city, categoryId);
        } else {
            cleaners = cleanerService.findAvailable();
        }
        return ResponseEntity.ok(cleaners);
    }

    @GetMapping("/searchGeo")
    public ResponseEntity<?> searchCleanersGeo(
            @RequestParam("lat") Double lat,
            @RequestParam("lng") Double lng,
            @RequestParam(value = "surface", required = false) Integer surface,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "startTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam(value = "endTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {

        List<com.sweet.home.sweethome.dto.CleanerSearchResponseDto> response = cleanerService.searchCleaners(lat, lng,
                surface, date, startTime, endTime);
        return ResponseEntity.ok(response);
    }

    // Public: View cleaner profile
    @GetMapping("/{id}")
    public ResponseEntity<?> getCleanerProfile(@PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        Cleaner cleaner = cleanerService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cleaner not found"));

        boolean hasReviewed = false;
        if (userDetails != null) {
            User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
            if (currentUser != null) {
                hasReviewed = reviewService.hasReviewed(currentUser.getId(), id);
            }
        }

        return ResponseEntity.ok(Map.of(
                "cleaner", cleaner,
                "reviews", reviewService.getReviewsForUser(id),
                "averageRating", reviewService.getAverageRating(id),
                "reviewCount", reviewService.getReviewCount(id),
                "hasReviewed", hasReviewed));
    }
}
