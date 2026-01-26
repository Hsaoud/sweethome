package com.sweet.home.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the landing page.
 */
@DisplayName("Landing Page Tests")
class LandingPageTest extends BaseSeleniumTest {

    @Test
    @DisplayName("Landing page loads with hero section")
    void landingPageLoadsWithHeroSection() {
        navigateTo("/");

        // Verify hero title
        String heroTitle = getText(By.cssSelector(".hero h1"));
        assertThat(heroTitle).contains("Find Trusted Cleaners");

        // Verify search box is present
        assertThat(isElementPresent(By.cssSelector(".search-box"))).isTrue();

        // Verify navigation links
        assertThat(isElementPresent(By.linkText("Find Cleaners"))).isTrue();
        assertThat(isElementPresent(By.linkText("Login"))).isTrue();
    }

    @Test
    @DisplayName("Navigation to cleaners page works")
    void navigationToCleanersPageWorks() {
        navigateTo("/");
        click(By.linkText("Find Cleaners"));

        waitForUrl("/cleaners");
        String pageTitle = getText(By.cssSelector(".section-title"));
        assertThat(pageTitle).contains("Find Your Perfect Cleaner");
    }

    @Test
    @DisplayName("Search form navigates to cleaners with filters")
    void searchFormNavigatesToCleanersWithFilters() {
        navigateTo("/");

        type(By.cssSelector(".search-box input[name='city']"), "Paris");
        click(By.cssSelector(".search-box button[type='submit']"));

        waitForUrl("/cleaners");
        assertThat(driver.getCurrentUrl()).contains("city=Paris");
    }

    @Test
    @DisplayName("How it works section is visible")
    void howItWorksSectionIsVisible() {
        navigateTo("/");

        assertThat(getText(By.cssSelector(".section-title"))).contains("Featured Cleaners");
    }

    @Test
    @DisplayName("Footer contains company information")
    void footerContainsCompanyInformation() {
        navigateTo("/");

        String footerText = getText(By.cssSelector(".footer"));
        assertThat(footerText).contains("Sweet Home");
    }
}
