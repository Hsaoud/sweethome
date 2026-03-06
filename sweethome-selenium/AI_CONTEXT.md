# AI Context: Sweet Home End-to-End Tests (sweethome-selenium)

## Overview
This contains the **Selenium End-to-End (E2E)** automated test suite for the Sweet Home application. This project uses Java to drive automated UI tests against the running front-end and back-end integration.

## Technical Stack
- **Language**: Java 
- **Tooling**: Selenium WebDriver
- **Build Tool**: Maven (`pom.xml`, `./mvnw`)

## Key Details
- Tests the complete user flows: registering, logging in, browsing categories, booking services (Homers) or listing availability (Cleaners).
- Requires both `sweethome-front` (UI) and `sweethome-back` (API/DB) to be running and accessible.

## Testing Strategy
- Tests here are integration/E2E level, not unit level. They simulate actual user interactions.
- Asserts DOM elements and resulting application states.
- Run via Maven commands once a local staging environment is available (`./mvnw test`).
