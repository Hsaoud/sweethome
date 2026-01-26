package com.sweet.home.sweethome.config;

import com.sweet.home.sweethome.model.*;
import com.sweet.home.sweethome.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Initialize database with sample categories, skills, and demo users.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final SkillRepository skillRepository;
    private final CleanerRepository cleanerRepository;
    private final HomerRepository homerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Only initialize if no data exists
        if (categoryRepository.count() > 0) {
            return;
        }

        // Create Categories
        Category generalCleaning = categoryRepository.save(new Category("General Cleaning", "Regular house cleaning"));
        Category deepCleaning = categoryRepository
                .save(new Category("Deep Cleaning", "Thorough cleaning of all areas"));
        Category moveInOut = categoryRepository.save(new Category("Move In/Out", "Cleaning for moving"));
        Category officeCleaning = categoryRepository.save(new Category("Office Cleaning", "Commercial office spaces"));
        Category postConstruction = categoryRepository
                .save(new Category("Post-Construction", "After renovation cleaning"));
        Category windowCleaning = categoryRepository
                .save(new Category("Window Cleaning", "Professional window washing"));

        // Create Skills
        Skill carpetCleaning = skillRepository.save(new Skill("Carpet Cleaning", "Deep carpet cleaning"));
        Skill kitchenDeep = skillRepository.save(new Skill("Kitchen Deep Clean", "Oven, fridge, appliances"));
        Skill bathroomDeep = skillRepository.save(new Skill("Bathroom Deep Clean", "Thorough bathroom sanitization"));
        Skill petFriendly = skillRepository.save(new Skill("Pet Friendly", "Experience with pet hair and odors"));
        Skill ecoProducts = skillRepository.save(new Skill("Eco-Friendly Products", "Uses green cleaning products"));
        Skill organizing = skillRepository.save(new Skill("Organizing", "Home organization services"));
        Skill laundry = skillRepository.save(new Skill("Laundry & Ironing", "Clothing care services"));

        // Create Demo Cleaner
        Cleaner demoCleaner = new Cleaner();
        demoCleaner.setEmail("cleaner@demo.com");
        demoCleaner.setPassword(passwordEncoder.encode("demo123"));
        demoCleaner.setFirstName("Marie");
        demoCleaner.setLastName("Dupont");
        demoCleaner.setPhone("+33 6 12 34 56 78");
        demoCleaner.setRole(Role.CLEANER);
        demoCleaner.setEnabled(true);
        demoCleaner.setHeadline("Professional House Cleaner with 8+ Years Experience");
        demoCleaner.setBio(
                "Hello! I'm Marie, a dedicated cleaning professional who takes pride in transforming homes. With over 8 years of experience, I specialize in deep cleaning, move-in/out services, and regular maintenance. I use eco-friendly products and pay attention to every detail. Your satisfaction is my priority!");
        demoCleaner.setCity("Paris");
        demoCleaner.setServiceArea("Paris, Boulogne-Billancourt, Neuilly");
        demoCleaner.setHourlyRate(new BigDecimal("28.00"));
        demoCleaner.setExperienceYears(8);
        demoCleaner.setAvailable(true);
        demoCleaner.setCategories(Set.of(generalCleaning, deepCleaning, moveInOut));
        demoCleaner.setSkills(Set.of(kitchenDeep, bathroomDeep, ecoProducts, organizing));
        cleanerRepository.save(demoCleaner);

        // Create second demo cleaner
        Cleaner demoCleaner2 = new Cleaner();
        demoCleaner2.setEmail("jean@demo.com");
        demoCleaner2.setPassword(passwordEncoder.encode("demo123"));
        demoCleaner2.setFirstName("Jean");
        demoCleaner2.setLastName("Martin");
        demoCleaner2.setRole(Role.CLEANER);
        demoCleaner2.setEnabled(true);
        demoCleaner2.setHeadline("Expert Window & Office Cleaner");
        demoCleaner2.setBio("Specialized in commercial spaces and window cleaning. Available for regular contracts.");
        demoCleaner2.setCity("Lyon");
        demoCleaner2.setHourlyRate(new BigDecimal("32.00"));
        demoCleaner2.setExperienceYears(5);
        demoCleaner2.setAvailable(true);
        demoCleaner2.setCategories(Set.of(windowCleaning, officeCleaning, postConstruction));
        demoCleaner2.setSkills(Set.of(carpetCleaning, petFriendly));
        cleanerRepository.save(demoCleaner2);

        // Create Demo Homer
        Homer demoHomer = new Homer();
        demoHomer.setEmail("homer@demo.com");
        demoHomer.setPassword(passwordEncoder.encode("demo123"));
        demoHomer.setFirstName("Pierre");
        demoHomer.setLastName("Bernard");
        demoHomer.setPhone("+33 6 98 76 54 32");
        demoHomer.setRole(Role.HOMER);
        demoHomer.setEnabled(true);
        demoHomer.setAddress("42 Rue de la Paix");
        demoHomer.setCity("Paris");
        demoHomer.setPostalCode("75002");
        homerRepository.save(demoHomer);

        System.out.println("✅ Database initialized with sample data!");
        System.out.println("   Demo accounts:");
        System.out.println("   - Homer: homer@demo.com / demo123");
        System.out.println("   - Cleaner: cleaner@demo.com / demo123");
    }
}
