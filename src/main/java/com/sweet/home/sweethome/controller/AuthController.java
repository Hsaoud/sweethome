package com.sweet.home.sweethome.controller;

import com.sweet.home.sweethome.dto.CleanerRegistrationDto;
import com.sweet.home.sweethome.dto.HomerRegistrationDto;
import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.model.Homer;
import com.sweet.home.sweethome.service.AuthService;
import com.sweet.home.sweethome.service.CategoryService;
import com.sweet.home.sweethome.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for authentication pages: login and registration.
 */
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CategoryService categoryService;
    private final SkillService skillService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register/homer")
    public String showHomerRegistration(Model model) {
        model.addAttribute("registration", new HomerRegistrationDto());
        return "auth/register-homer";
    }

    @PostMapping("/register/homer")
    public String registerHomer(@Valid @ModelAttribute("registration") HomerRegistrationDto dto,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "auth/register-homer";
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.registration", "Passwords do not match");
            return "auth/register-homer";
        }

        if (authService.emailExists(dto.getEmail())) {
            result.rejectValue("email", "error.registration", "Email already registered");
            return "auth/register-homer";
        }

        Homer homer = new Homer();
        homer.setEmail(dto.getEmail());
        homer.setPassword(dto.getPassword());
        homer.setFirstName(dto.getFirstName());
        homer.setLastName(dto.getLastName());
        homer.setPhone(dto.getPhone());
        homer.setAddress(dto.getAddress());
        homer.setCity(dto.getCity());
        homer.setPostalCode(dto.getPostalCode());

        authService.registerHomer(homer);

        redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
        return "redirect:/login";
    }

    @GetMapping("/register/cleaner")
    public String showCleanerRegistration(Model model) {
        model.addAttribute("registration", new CleanerRegistrationDto());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("skills", skillService.findAll());
        return "auth/register-cleaner";
    }

    @PostMapping("/register/cleaner")
    public String registerCleaner(@Valid @ModelAttribute("registration") CleanerRegistrationDto dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("skills", skillService.findAll());
            return "auth/register-cleaner";
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.registration", "Passwords do not match");
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("skills", skillService.findAll());
            return "auth/register-cleaner";
        }

        if (authService.emailExists(dto.getEmail())) {
            result.rejectValue("email", "error.registration", "Email already registered");
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("skills", skillService.findAll());
            return "auth/register-cleaner";
        }

        Cleaner cleaner = new Cleaner();
        cleaner.setEmail(dto.getEmail());
        cleaner.setPassword(dto.getPassword());
        cleaner.setFirstName(dto.getFirstName());
        cleaner.setLastName(dto.getLastName());
        cleaner.setPhone(dto.getPhone());
        cleaner.setHeadline(dto.getHeadline());
        cleaner.setBio(dto.getBio());
        cleaner.setCity(dto.getCity());
        cleaner.setHourlyRate(dto.getHourlyRate());
        cleaner.setExperienceYears(dto.getExperienceYears());

        if (dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()) {
            cleaner.setCategories(categoryService.findByIds(dto.getCategoryIds()));
        }
        if (dto.getSkillIds() != null && !dto.getSkillIds().isEmpty()) {
            cleaner.setSkills(skillService.findByIds(dto.getSkillIds()));
        }

        authService.registerCleaner(cleaner);

        redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
        return "redirect:/login";
    }
}
