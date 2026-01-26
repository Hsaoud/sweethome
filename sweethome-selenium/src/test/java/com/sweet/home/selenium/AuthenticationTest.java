package com.sweet.home.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for authentication (login/logout).
 */
@DisplayName("Authentication Tests")
class AuthenticationTest extends BaseSeleniumTest {

    private static final String HOMER_EMAIL = "homer@demo.com";
    private static final String CLEANER_EMAIL = "cleaner@demo.com";
    private static final String DEMO_PASSWORD = "demo123";

    @Test
    @DisplayName("Login page loads correctly")
    void loginPageLoadsCorrectly() {
        navigateTo("/login");

        String pageTitle = getText(By.cssSelector(".auth-title"));
        assertThat(pageTitle).contains("Welcome Back");

        assertThat(isElementPresent(By.id("username"))).isTrue();
        assertThat(isElementPresent(By.id("password"))).isTrue();
    }

    @Test
    @DisplayName("Homer can login and is redirected to homer dashboard")
    void homerCanLoginAndRedirectsToDashboard() {
        login(HOMER_EMAIL, DEMO_PASSWORD);

        waitForUrl("/homer/dashboard");
        String welcomeText = getText(By.cssSelector(".dashboard-title"));
        assertThat(welcomeText).contains("Welcome");
    }

    @Test
    @DisplayName("Cleaner can login and is redirected to cleaner dashboard")
    void cleanerCanLoginAndRedirectsToDashboard() {
        login(CLEANER_EMAIL, DEMO_PASSWORD);

        waitForUrl("/cleaner/dashboard");
        String welcomeText = getText(By.cssSelector(".dashboard-title"));
        assertThat(welcomeText).contains("Welcome");
    }

    @Test
    @DisplayName("Invalid login shows error message")
    void invalidLoginShowsErrorMessage() {
        navigateTo("/login");
        type(By.id("username"), "invalid@email.com");
        type(By.id("password"), "wrongpassword");
        click(By.cssSelector("button[type='submit']"));

        // Should stay on login page with error
        assertThat(driver.getCurrentUrl()).contains("/login");
        String errorMessage = getText(By.cssSelector(".alert-danger"));
        assertThat(errorMessage).contains("Invalid");
    }

    @Test
    @DisplayName("User can logout successfully")
    void userCanLogoutSuccessfully() {
        login(HOMER_EMAIL, DEMO_PASSWORD);
        waitForUrl("/homer/dashboard");

        logout();

        waitForUrl("/login");
        String successMessage = getText(By.cssSelector(".alert-success"));
        assertThat(successMessage).contains("logged out");
    }

    @Test
    @DisplayName("Unauthenticated user cannot access homer dashboard")
    void unauthenticatedUserCannotAccessHomerDashboard() {
        navigateTo("/homer/dashboard");

        // Should be redirected to login
        waitForUrl("/login");
    }

    @Test
    @DisplayName("Unauthenticated user cannot access cleaner dashboard")
    void unauthenticatedUserCannotAccessCleanerDashboard() {
        navigateTo("/cleaner/dashboard");

        // Should be redirected to login
        waitForUrl("/login");
    }

    @Test
    @DisplayName("Homer cannot access cleaner dashboard")
    void homerCannotAccessCleanerDashboard() {
        login(HOMER_EMAIL, DEMO_PASSWORD);
        waitForUrl("/homer/dashboard");

        navigateTo("/cleaner/dashboard");

        // Should be denied access
        assertThat(driver.getCurrentUrl()).contains("access-denied");
    }

    @Test
    @DisplayName("Cleaner cannot access homer dashboard")
    void cleanerCannotAccessHomerDashboard() {
        login(CLEANER_EMAIL, DEMO_PASSWORD);
        waitForUrl("/cleaner/dashboard");

        navigateTo("/homer/dashboard");

        // Should be denied access
        assertThat(driver.getCurrentUrl()).contains("access-denied");
    }
}
