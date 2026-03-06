# AI Context: Sweet Home Database (sweethome-postgres)

## Overview
This defines the PostgreSQL **Database** structures and basic Docker Compose configuration for the Sweet Home project. 

## Technical Stack
- **Database**: PostgreSQL
- **Provisioning**: Docker Compose (`docker-compose.yml`)
- **Initialization**: Standard Init Script (`schema.sql`)

## Key Details
- **schema.sql**: Defines the database schema, including tables like `users`, `homers`, `cleaners`, `categories`, `skills`, and `reviews`. 
- **Relationships**: `homers` and `cleaners` inherit their `id` from the `users` table logically (foreign key referencing users(id)).
- The backend relies on this schema being created correctly to map to JPA Entities.

## Testing Strategy
- There are no dedicated unit tests for these SQL creation scripts within this folder.
- Validation is performed through backend tests (`@DataJpaTest`) and Integration tests using Testcontainers or an active instance.
