package com.sweet.home.sweethome.controller;

import com.sweet.home.sweethome.model.Homer;
import com.sweet.home.sweethome.model.User;
import com.sweet.home.sweethome.service.HomerService;
import com.sweet.home.sweethome.service.ReviewService;
import com.sweet.home.sweethome.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for homer-related pages: profile, dashboard.
 */
@Controller
@RequiredArgsConstructor
public class HomerController {

    private final HomerService homerService;
    private final ReviewService reviewService;
    private final UserService userService;

    // Public: View homer profile
    @GetMapping("/homers/{id}")
    public String viewHomerProfile(@PathVariable Long id, Model model) {
        Homer homer = homerService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Homer not found"));

        model.addAttribute("homer", homer);
        model.addAttribute("reviews", reviewService.getReviewsForUser(id));
        model.addAttribute("averageRating", reviewService.getAverageRating(id));
        model.addAttribute("reviewCount", reviewService.getReviewCount(id));

        return "homer/profile";
    }

    // Homer: Dashboard
    @GetMapping("/homer/dashboard")
    public String homerDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        model.addAttribute("user", user);
        model.addAttribute("reviews", reviewService.getReviewsForUser(user.getId()));
        model.addAttribute("reviewsGiven", reviewService.getReviewsByReviewer(user));

        return "homer/dashboard";
    }

    // Homer: Edit profile form
    @GetMapping("/homer/profile/edit")
    public String editProfileForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!(user instanceof Homer)) {
            return "redirect:/";
        }

        model.addAttribute("homer", user);
        return "homer/edit-profile";
    }

    // Homer: Update profile
    @PostMapping("/homer/profile/edit")
    public String updateProfile(@AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute Homer updatedHomer,
            RedirectAttributes redirectAttributes) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!(user instanceof Homer homer)) {
            return "redirect:/";
        }

        homer.setFirstName(updatedHomer.getFirstName());
        homer.setLastName(updatedHomer.getLastName());
        homer.setPhone(updatedHomer.getPhone());
        homer.setAddress(updatedHomer.getAddress());
        homer.setCity(updatedHomer.getCity());
        homer.setPostalCode(updatedHomer.getPostalCode());
        homer.setAdditionalInfo(updatedHomer.getAdditionalInfo());

        homerService.save(homer);

        redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        return "redirect:/homer/dashboard";
    }
}
