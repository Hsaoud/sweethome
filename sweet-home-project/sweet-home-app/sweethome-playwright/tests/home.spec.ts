import { test, expect } from '@playwright/test';

test.describe('Home Page functionality', () => {

    test('should have expected titles and call to actions', async ({ page }) => {
        await page.goto('/');

        // Main heading text
        await expect(page.locator('h1')).toContainText('Data driven');
        await expect(page.locator('h1')).toContainText('home cleaning');

        // Check for the call to action buttons
        const findCleanersBtn = page.getByRole('link', { name: /Find Cleaners/i }).first();
        await expect(findCleanersBtn).toBeVisible();

        const becomeCleanerBtn = page.getByRole('link', { name: /Become a Cleaner/i }).first();
        await expect(becomeCleanerBtn).toBeVisible();
    });

    test('should navigate to cleaner registration when clicking Become a Cleaner', async ({ page }) => {
        await page.goto('/');

        const becomeCleanerBtn = page.getByRole('link', { name: /Become a Cleaner/i }).first();
        await becomeCleanerBtn.click();

        // Ensure the URL changed appropriately
        await expect(page).toHaveURL(/.*register\/cleaner/);

        // Ensure the registration page loaded
        await expect(page.locator('h2')).toContainText('Join as a Cleaner');
    });

    test('should navigate to cleaners list when clicking Find Cleaners', async ({ page }) => {
        await page.goto('/');

        const findCleanersBtn = page.getByRole('link', { name: /Find Cleaners/i }).first();
        await findCleanersBtn.click();

        // Ensure the URL changed appropriately
        await expect(page).toHaveURL(/.*cleaners/);
    });

});
