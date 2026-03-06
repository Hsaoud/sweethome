# AI Context: Sweet Home Frontend (sweethome-front)

## Overview
This is the **Frontend** Single Page Application (SPA) for the Sweet Home project. It interacts with the `sweethome-back` REST APIs to provide the user interface for end users, both Homers (clients looking for cleaners) and Cleaners (freelancers offering services).

## Technical Stack
- **Language**: TypeScript
- **Framework**: Angular 17+ (Uses standalone components and new control flow where applicable).
- **Styling**: Tailwind CSS & plain SCSS.
- **Server Side Rendering (SSR)**: Enabled (`@angular/ssr`), `server.ts` exists.
- **Package Manager**: npm (`package.json`)

## Key Directories
- `src/app/pages`: Major views/components (e.g., Home, Login, Search).
- `src/app/layout`: Structural components (Header, Footer, Navigation).
- `src/app/services`: Angular Injectable services, mainly for HTTP calls to the backend.
- `src/app/models`: Interfaces/types corresponding to backend DTOs.
- `src/app/app.routes.ts`: Main routing configuration for the application.

## Testing Strategy
- Unit tests run on **Jasmine & Karma**.
- Component tests should check DOM logic, using `TestBed` and `fixture.detectChanges()`.
- Service tests use `HttpTestingController` to mock API responses.
- Run tests via `npm run test`.
