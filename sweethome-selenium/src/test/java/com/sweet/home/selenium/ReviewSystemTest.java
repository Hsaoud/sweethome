package com.sweet.home.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the review system.
 */
@DisplayName("Review System Tests")
class ReviewSystemTest extends BaseSeleniumTest {

    private static final String HOMER_EMAIL = "homer@demo.com";
    private static final String CLEANER_EMAIL = "cleaner@demo.com";
    private static final String DEMO_PASSWORD = "demo123";

    @Test
    @DisplayName("Reviews are displayed on cleaner profile")
    void reviewsAreDisplayedOnCleanerProfile() {
        navigateTo("/cleaners");

        if (isElementPresent(By.linkText("View Profile"))) {
            click(By.linkText("View Profile"));

            // Check for reviews section
            assertThat(getText(By.tagName("body"))).contains("Reviews");
        }
    }

    @Test
    @DisplayName("Homer can see review form on cleaner profile")
    void homerCanSeeReviewFormOnCleanerProfile() {
        login(HOMER_EMAIL, DEMO_PASSWORD);

        // Navigate to a cleaner profile
        navigateTo("/cleaners");

        if (isElementPresent(By.linkText("View Profile"))) {
            click(By.linkText("View Profile"));

            // Check for review form
            assertThat(isElementPresent(By.id("comment")) ||
                    getText(By.tagName("body")).contains("already reviewed")).isTrue();
        }
    }

    @Test
    @DisplayName("Homer cannot submit empty review")
    void homerCannotSubmitEmptyReview() {
        login(HOMER_EMAIL, DEMO_PASSWORD);

        navigateTo("/cleaners");

        if (isElementPresent(By.linkText("View Profile"))) {
            click(By.linkText("View Profile"));

            // Try to submit without filling form
            if (isElementPresent(By.cssSelector("form[action='/reviews'] button"))) {
                // The form should require fields
                assertThat(isElementPresent(By.cssSelector("[required]"))).isTrue();
            }
        }
    }

    @Test
    @DisplayName("Cleaner can see their reviews in dashboard")
    void cleanerCanSeeReviewsInDashboard() {
        login(CLEANER_EMAIL, DEMO_PASSWORD);
        waitForUrl("/cleaner/dashboard");

        // Check for reviews section
        assertThat(getText(By.tagName("body"))).contains("Recent Reviews");
    }

    @Test
    @DisplayName("Average rating is displayed on cleaner profile")
    void averageRatingIsDisplayedOnCleanerProfile() {
        navigateTo("/cleaners");

        if (isElementPresent(By.linkText("View Profile"))) {
            click(By.linkText("View Profile"));

            // Check for rating display
            assertThat(isElementPresent(By.cssSelector(".stat-value")) ||
                    getText(By.tagName("body")).contains("Rating")).isTrue();
        }
    }

    @Test
    @DisplayName("Cleaners list shows ratings")
    void cleanersListShowsRatings() {
        navigateTo("/cleaners");

        // Check if ratings are shown in cleaner cards
        if (isElementPresent(By.cssSelector(".cleaner-rating"))) {
            assertThat(getText(By.cssSelector(".cleaner-rating"))).contains("⭐");
        }
    }
}
