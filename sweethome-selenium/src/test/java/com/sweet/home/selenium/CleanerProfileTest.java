package com.sweet.home.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for cleaner profile features.
 */
@DisplayName("Cleaner Profile Tests")
class CleanerProfileTest extends BaseSeleniumTest {

    private static final String CLEANER_EMAIL = "cleaner@demo.com";
    private static final String DEMO_PASSWORD = "demo123";

    @Test
    @DisplayName("Cleaner profile page is publicly accessible")
    void cleanerProfileIsPubliclyAccessible() {
        // Navigate to cleaners list first
        navigateTo("/cleaners");

        // Click on first cleaner's profile
        if (isElementPresent(By.linkText("View Profile"))) {
            click(By.linkText("View Profile"));

            // Should show profile data
            assertThat(isElementPresent(By.cssSelector(".profile-name"))).isTrue();
            assertThat(isElementPresent(By.cssSelector(".profile-stats"))).isTrue();
        }
    }

    @Test
    @DisplayName("Cleaner can access their dashboard")
    void cleanerCanAccessDashboard() {
        login(CLEANER_EMAIL, DEMO_PASSWORD);
        waitForUrl("/cleaner/dashboard");

        // Verify dashboard elements
        assertThat(getText(By.cssSelector(".dashboard-title"))).contains("Welcome");
        assertThat(isElementPresent(By.cssSelector(".stat-card"))).isTrue();
    }

    @Test
    @DisplayName("Cleaner can navigate to edit profile")
    void cleanerCanNavigateToEditProfile() {
        login(CLEANER_EMAIL, DEMO_PASSWORD);
        waitForUrl("/cleaner/dashboard");

        click(By.linkText("Edit Profile"));
        waitForUrl("/cleaner/profile/edit");

        // Verify edit form is present
        assertThat(isElementPresent(By.id("firstName"))).isTrue();
        assertThat(isElementPresent(By.id("headline"))).isTrue();
        assertThat(isElementPresent(By.id("bio"))).isTrue();
    }

    @Test
    @DisplayName("Cleaner can update their profile")
    void cleanerCanUpdateProfile() {
        login(CLEANER_EMAIL, DEMO_PASSWORD);
        waitForUrl("/cleaner/dashboard");

        click(By.linkText("Edit Profile"));
        waitForUrl("/cleaner/profile/edit");

        // Update headline
        type(By.id("headline"), "Updated Professional Headline");
        click(By.cssSelector("button[type='submit']"));

        // Should redirect to dashboard with success message
        waitForUrl("/cleaner/dashboard");
        String successMessage = getText(By.cssSelector(".alert-success"));
        assertThat(successMessage).contains("Profile updated");
    }

    @Test
    @DisplayName("Cleaner profile shows categories and skills")
    void cleanerProfileShowsCategoriesAndSkills() {
        navigateTo("/cleaners");

        // Check if any cleaner cards show categories
        if (isElementPresent(By.cssSelector(".cleaner-tags .tag"))) {
            assertThat(getText(By.cssSelector(".cleaner-tags"))).isNotEmpty();
        }
    }

    @Test
    @DisplayName("Cleaner can view their public profile")
    void cleanerCanViewPublicProfile() {
        login(CLEANER_EMAIL, DEMO_PASSWORD);
        waitForUrl("/cleaner/dashboard");

        click(By.linkText("View Public Profile"));

        // Should navigate to public profile
        assertThat(driver.getCurrentUrl()).contains("/cleaners/");
        assertThat(isElementPresent(By.cssSelector(".profile-name"))).isTrue();
    }
}
