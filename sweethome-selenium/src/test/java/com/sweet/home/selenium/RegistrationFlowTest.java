package com.sweet.home.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for user registration flows.
 */
@DisplayName("Registration Flow Tests")
class RegistrationFlowTest extends BaseSeleniumTest {

    @Test
    @DisplayName("Homer registration page loads correctly")
    void homerRegistrationPageLoads() {
        navigateTo("/register/homer");

        String pageTitle = getText(By.cssSelector(".auth-title"));
        assertThat(pageTitle).contains("Join as a Homer");

        // Verify form fields are present
        assertThat(isElementPresent(By.id("email"))).isTrue();
        assertThat(isElementPresent(By.id("password"))).isTrue();
        assertThat(isElementPresent(By.id("firstName"))).isTrue();
        assertThat(isElementPresent(By.id("lastName"))).isTrue();
    }

    @Test
    @DisplayName("Cleaner registration page loads correctly")
    void cleanerRegistrationPageLoads() {
        navigateTo("/register/cleaner");

        String pageTitle = getText(By.cssSelector(".auth-title"));
        assertThat(pageTitle).contains("Join as a Cleaner");

        // Verify professional fields are present
        assertThat(isElementPresent(By.id("headline"))).isTrue();
        assertThat(isElementPresent(By.id("bio"))).isTrue();
        assertThat(isElementPresent(By.id("hourlyRate"))).isTrue();
    }

    @Test
    @DisplayName("Homer can register successfully")
    void homerCanRegisterSuccessfully() {
        String uniqueEmail = "homer-" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";

        navigateTo("/register/homer");

        type(By.id("email"), uniqueEmail);
        type(By.id("password"), "password123");
        type(By.id("confirmPassword"), "password123");
        type(By.id("firstName"), "Test");
        type(By.id("lastName"), "Homer");
        type(By.id("city"), "Paris");

        click(By.cssSelector("button[type='submit']"));

        // Should redirect to login with success message
        waitForUrl("/login");
        String successMessage = getText(By.cssSelector(".alert-success"));
        assertThat(successMessage).contains("Registration successful");
    }

    @Test
    @DisplayName("Cleaner can register successfully")
    void cleanerCanRegisterSuccessfully() {
        String uniqueEmail = "cleaner-" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";

        navigateTo("/register/cleaner");

        type(By.id("email"), uniqueEmail);
        type(By.id("password"), "password123");
        type(By.id("confirmPassword"), "password123");
        type(By.id("firstName"), "Test");
        type(By.id("lastName"), "Cleaner");
        type(By.id("headline"), "Professional Cleaner");
        type(By.id("city"), "Lyon");

        click(By.cssSelector("button[type='submit']"));

        // Should redirect to login with success message
        waitForUrl("/login");
        String successMessage = getText(By.cssSelector(".alert-success"));
        assertThat(successMessage).contains("Registration successful");
    }

    @Test
    @DisplayName("Registration shows error for mismatched passwords")
    void registrationShowsErrorForMismatchedPasswords() {
        navigateTo("/register/homer");

        type(By.id("email"), "test@example.com");
        type(By.id("password"), "password123");
        type(By.id("confirmPassword"), "different123");
        type(By.id("firstName"), "Test");
        type(By.id("lastName"), "User");

        click(By.cssSelector("button[type='submit']"));

        // Should stay on registration page with error
        assertThat(driver.getCurrentUrl()).contains("/register/homer");
        String errorText = getText(By.cssSelector(".invalid-feedback"));
        assertThat(errorText).contains("Passwords do not match");
    }

    @Test
    @DisplayName("Registration shows error for existing email")
    void registrationShowsErrorForExistingEmail() {
        // Using demo account email that already exists
        navigateTo("/register/homer");

        type(By.id("email"), "homer@demo.com");
        type(By.id("password"), "password123");
        type(By.id("confirmPassword"), "password123");
        type(By.id("firstName"), "Test");
        type(By.id("lastName"), "User");

        click(By.cssSelector("button[type='submit']"));

        // Should stay on registration page with error
        assertThat(driver.getCurrentUrl()).contains("/register/homer");
        String errorText = getText(By.cssSelector(".invalid-feedback"));
        assertThat(errorText).contains("already registered");
    }
}
