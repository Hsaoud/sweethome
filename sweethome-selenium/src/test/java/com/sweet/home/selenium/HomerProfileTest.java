package com.sweet.home.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for homer profile features.
 */
@DisplayName("Homer Profile Tests")
class HomerProfileTest extends BaseSeleniumTest {

    private static final String HOMER_EMAIL = "homer@demo.com";
    private static final String DEMO_PASSWORD = "demo123";

    @Test
    @DisplayName("Homer can access their dashboard")
    void homerCanAccessDashboard() {
        login(HOMER_EMAIL, DEMO_PASSWORD);
        waitForUrl("/homer/dashboard");

        // Verify dashboard elements
        assertThat(getText(By.cssSelector(".dashboard-title"))).contains("Welcome");
        assertThat(isElementPresent(By.cssSelector(".stat-card"))).isTrue();
    }

    @Test
    @DisplayName("Homer can navigate to edit profile")
    void homerCanNavigateToEditProfile() {
        login(HOMER_EMAIL, DEMO_PASSWORD);
        waitForUrl("/homer/dashboard");

        click(By.linkText("Edit Profile"));
        waitForUrl("/homer/profile/edit");

        // Verify edit form is present
        assertThat(isElementPresent(By.id("firstName"))).isTrue();
        assertThat(isElementPresent(By.id("address"))).isTrue();
        assertThat(isElementPresent(By.id("city"))).isTrue();
    }

    @Test
    @DisplayName("Homer can update their profile")
    void homerCanUpdateProfile() {
        login(HOMER_EMAIL, DEMO_PASSWORD);
        waitForUrl("/homer/dashboard");

        click(By.linkText("Edit Profile"));
        waitForUrl("/homer/profile/edit");

        // Update address
        type(By.id("address"), "123 Updated Street");
        click(By.cssSelector("button[type='submit']"));

        // Should redirect to dashboard with success message
        waitForUrl("/homer/dashboard");
        String successMessage = getText(By.cssSelector(".alert-success"));
        assertThat(successMessage).contains("Profile updated");
    }

    @Test
    @DisplayName("Homer dashboard shows find cleaners button")
    void homerDashboardShowsFindCleanersButton() {
        login(HOMER_EMAIL, DEMO_PASSWORD);
        waitForUrl("/homer/dashboard");

        assertThat(isElementPresent(By.linkText("Find Cleaners"))).isTrue();
    }

    @Test
    @DisplayName("Homer can navigate to cleaners list from dashboard")
    void homerCanNavigateToCleanersFromDashboard() {
        login(HOMER_EMAIL, DEMO_PASSWORD);
        waitForUrl("/homer/dashboard");

        click(By.linkText("Find Cleaners"));
        waitForUrl("/cleaners");

        assertThat(getText(By.cssSelector(".section-title"))).contains("Find Your Perfect Cleaner");
    }
}
