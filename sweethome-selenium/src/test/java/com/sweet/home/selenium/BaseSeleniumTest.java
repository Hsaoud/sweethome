package com.sweet.home.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base class for all Selenium tests.
 * Provides WebDriver setup, teardown, and common helper methods.
 */
public abstract class BaseSeleniumTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected static final String BASE_URL = "http://localhost:4200";
    protected static final Duration TIMEOUT = Duration.ofSeconds(10);

    @BeforeAll
    static void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Run headless for CI environments
        if (System.getenv("CI") != null) {
            options.addArguments("--headless");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, TIMEOUT);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Helper methods

    protected void navigateTo(String path) {
        driver.get(BASE_URL + path);
    }

    protected WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected WebElement findClickableElement(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        try {
            findClickableElement(locator).click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            // Fallback to JS click if element is intercepted (e.g. by sticky header)
            WebElement element = findElement(locator);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void type(By locator, String text) {
        WebElement element = findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void submit(By locator) {
        findElement(locator).submit();
    }

    protected String getText(By locator) {
        return findElement(locator).getText();
    }

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected void waitForUrl(String urlContains) {
        wait.until(ExpectedConditions.urlContains(urlContains));
    }

    protected void login(String email, String password) {
        navigateTo("/login");
        type(By.id("username"), email);
        type(By.id("password"), password);
        click(By.cssSelector("button[type='submit']"));
    }

    protected void logout() {
        click(By.cssSelector("form[action='/logout'] button"));
    }
}
