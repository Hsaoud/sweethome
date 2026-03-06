# AI Context: Sweet Home End-to-End Tests (sweethome-selenium)

## Overview
This folder contains the **Playwright End-to-End (E2E)** automated test suite for the Sweet Home application. Note: Historically this was a Selenium project, but it has been completely rewritten in Playwright using TypeScript.

## Technical Stack
- **Language**: TypeScript
- **Tooling**: Playwright Test (`@playwright/test`)
- **Package Manager**: npm (`package.json`)

## Key Details
- Tests the complete user flows: registering, logging in, browsing categories, booking services (Homers) or listing availability (Cleaners).
- Requires both `sweethome-front` (UI) and `sweethome-back` (API/DB) to be running and accessible.
- Tests are located in the `tests/` directory.

## Testing Strategy
- Tests here are integration/E2E level, not unit level. They simulate actual user interactions.
- Asserts DOM elements and resulting application states.
- Run via `npx playwright test` once a local staging environment is available (usually frontend running on `localhost:4200`).
