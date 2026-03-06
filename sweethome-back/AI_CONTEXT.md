# AI Context: Sweet Home Backend (sweethome-back)

## Overview
This is the **Backend** application for the Sweet Home project (a BlaBlaCar-style platform for home cleaning services).
It serves REST APIs to the Angular frontend and connects to a PostgreSQL database.

## Technical Stack
- **Language**: Java 21
- **Framework**: Spring Boot 3.4.2
- **Components**: Spring Web, Spring Security, Spring Data JPA, Spring Validation
- **Build Tool**: Maven (`pom.xml`, `./mvnw`)
- **Database Engine**: PostgreSQL

## Key Directories
- `src/main/java/com/sweet/home/sweethome/controller`: REST controllers (API Endpoints).
- `src/main/java/com/sweet/home/sweethome/service`: Business logic handling.
- `src/main/java/com/sweet/home/sweethome/repository`: Database access (Spring Data JPA interfaces).
- `src/main/java/com/sweet/home/sweethome/model`: JPA Entities mapping to `users`, `cleaners`, `homers`, etc.
- `src/main/java/com/sweet/home/sweethome/dto`: Data Transfer Objects for API payloads.
- `src/main/java/com/sweet/home/sweethome/security`: JWT or session-based security configuration.

## Testing Strategy & TDD Constraint
**CRITICAL INSTRUCTION**: All future evolutions, feature developments, and bugfixes MUST be implemented using the **Test-Driven Development (TDD)** methodology.
**BACKEND-FIRST WORKFLOW**: Always implement the backend (domain models, repositories, services, controllers) with full TDD coverage BEFORE starting any frontend implementation for a given feature.
- Write the unit or component test *before* implementing the actual logic or feature.
- Ensure the test fails first, then write the minimal code required to make it pass, and finally refactor.
- Use **JUnit 5** and **Mockito** for unit testing (`@ExtendWith(MockitoExtension.class)`).
- Run tests via `./mvnw test`.
- Do not integrate DB for unit testing if possible; use mocks. If DB testing is required, use H2 memory DB or Testcontainers.
