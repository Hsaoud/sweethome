package com.sweet.home.sweethome.config;

import com.sweet.home.sweethome.model.*;
import com.sweet.home.sweethome.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Initialize database with sample categories, skills, and demo users.
 */
@Component
public class DataInitializer implements CommandLineRunner {

        private final CategoryRepository categoryRepository;
        private final SkillRepository skillRepository;
        private final CleanerRepository cleanerRepository;
        private final HomerRepository homerRepository;
        private final PasswordEncoder passwordEncoder;

        public DataInitializer(CategoryRepository categoryRepository, SkillRepository skillRepository,
                        CleanerRepository cleanerRepository, HomerRepository homerRepository,
                        PasswordEncoder passwordEncoder) {
                this.categoryRepository = categoryRepository;
                this.skillRepository = skillRepository;
                this.cleanerRepository = cleanerRepository;
                this.homerRepository = homerRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void run(String... args) {
                // Only initialize if no data exists
                if (categoryRepository.count() > 0) {
                        return;
                }

                // Create Categories
                Category generalCleaning = categoryRepository
                                .save(new Category("General Cleaning", "Regular house cleaning"));
                Category deepCleaning = categoryRepository
                                .save(new Category("Deep Cleaning", "Thorough cleaning of all areas"));
                Category moveInOut = categoryRepository.save(new Category("Move In/Out", "Cleaning for moving"));
                Category officeCleaning = categoryRepository
                                .save(new Category("Office Cleaning", "Commercial office spaces"));
                Category postConstruction = categoryRepository
                                .save(new Category("Post-Construction", "After renovation cleaning"));
                Category windowCleaning = categoryRepository
                                .save(new Category("Window Cleaning", "Professional window washing"));

                // Create Skills
                Skill carpetCleaning = skillRepository.save(new Skill("Carpet Cleaning", "Deep carpet cleaning"));
                Skill kitchenDeep = skillRepository.save(new Skill("Kitchen Deep Clean", "Oven, fridge, appliances"));
                Skill bathroomDeep = skillRepository
                                .save(new Skill("Bathroom Deep Clean", "Thorough bathroom sanitization"));
                Skill petFriendly = skillRepository
                                .save(new Skill("Pet Friendly", "Experience with pet hair and odors"));
                Skill ecoProducts = skillRepository
                                .save(new Skill("Eco-Friendly Products", "Uses green cleaning products"));
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
                demoCleaner.setPricePerSqm(new BigDecimal("28.00"));
                demoCleaner.setExperienceYears(8);
                demoCleaner.setAvailable(true);

                Set<Category> cats = new HashSet<>();
                cats.add(generalCleaning);
                cats.add(deepCleaning);
                cats.add(moveInOut);
                demoCleaner.setCategories(cats);

                Set<Skill> skills = new HashSet<>();
                skills.add(kitchenDeep);
                skills.add(bathroomDeep);
                skills.add(ecoProducts);
                skills.add(organizing);
                demoCleaner.setSkills(skills);

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
                demoCleaner2.setBio(
                                "Specialized in commercial spaces and window cleaning. Available for regular contracts.");
                demoCleaner2.setCity("Lyon");
                demoCleaner2.setPricePerSqm(new BigDecimal("32.00"));
                demoCleaner2.setExperienceYears(5);
                demoCleaner2.setAvailable(true);

                Set<Category> cats2 = new HashSet<>();
                cats2.add(windowCleaning);
                cats2.add(officeCleaning);
                cats2.add(postConstruction);
                demoCleaner2.setCategories(cats2);

                Set<Skill> skills2 = new HashSet<>();
                skills2.add(carpetCleaning);
                skills2.add(petFriendly);
                demoCleaner2.setSkills(skills2);

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
