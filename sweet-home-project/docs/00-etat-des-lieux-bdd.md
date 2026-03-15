# État des lieux BDD - Modèle Physique de Données (MPD)

Ce document décrit le schéma actuel de la base de données PostgreSQL pour le projet Sweet-Home.

## Diagramme Entité-Relation (ERD)

```mermaid
erDiagram
    users {
        bigint id PK
        timestamp created_at
        timestamp updated_at
        varchar(255) email UK
        varchar(255) password
        varchar(255) first_name
        varchar(255) last_name
        varchar(255) phone
        varchar(255) profile_image
        varchar(255) role
        boolean enabled
        double latitude
        double longitude
    }

    homers {
        bigint id PK, FK
        varchar(255) address
        varchar(255) city
        varchar(255) postal_code
        varchar(255) additional_info
    }

    cleaners {
        bigint id PK, FK
        boolean available
        varchar(2000) bio
        varchar(255) city
        integer experience_years
        varchar(500) headline
        numeric(38_2) hourly_rate
        varchar(255) service_area
        integer action_radius_km
        numeric(38_2) price_per_sqm
    }

    categories {
        bigint id PK
        varchar(255) name UK
        varchar(255) description
        varchar(255) icon
    }

    skills {
        bigint id PK
        varchar(255) name UK
        varchar(255) description
    }

    cleaner_categories {
        bigint cleaner_id PK, FK
        bigint category_id PK, FK
    }

    cleaner_skills {
        bigint cleaner_id PK, FK
        bigint skill_id PK, FK
    }

    reviews {
        bigint id PK
        integer rating
        varchar(1000) comment
        timestamp created_at
        bigint reviewer_id FK
        bigint reviewee_id FK
    }

    bookings {
        bigint id PK
        date date
        time start_time
        time end_time
        varchar(255) status
        integer surface
        numeric(38_2) total_price
        timestamp created_at
        timestamp updated_at
        bigint homer_id FK
        bigint cleaner_id FK
    }

    users ||--o| homers : "est un (héritage)"
    users ||--o| cleaners : "est un (héritage)"
    
    cleaners ||--o{ cleaner_categories : "possède"
    categories ||--o{ cleaner_categories : "appartient"
    
    cleaners ||--o{ cleaner_skills : "maîtrise"
    skills ||--o{ cleaner_skills : "requise"
    
    users ||--o{ reviews : "donne (reviewer)"
    users ||--o{ reviews : "reçoit (reviewee)"
    
    homers ||--o{ bookings : "réserve"
    cleaners ||--o{ bookings : "est réservé"
```

## Description des tables
- **users** : Table parente gérant l'authentification (email/mot de passe), les rôles (HOMER, CLEANER, ADMIN) et la géolocalisation.
- **homers** : Table enfant de `users` (héritage JOINED sur `id`). Contient les adresses des particuliers.
- **cleaners** : Table enfant de `users` (héritage JOINED sur `id`). Contient les informations des prestataires (tarif, rayon d'action, bio).
- **categories / skills** : Référentiels des types de ménage et des compétences. Liés aux cleaners par les tables de jointure `cleaner_categories` et `cleaner_skills`.
- **reviews** : Système de notation entre utilisateurs (rating de 1 à 5).
- **bookings** : Réservations de prestations (lié à un homer et un cleaner, avec surface et prix total).


-- Ajout au Cycle 1 --
Nouvelles modifications apportées par la feature : feature-auto-cycle-1.md

-- Ajout au Cycle 2 --
Nouvelles modifications apportées par la feature : feature-auto-cycle-2.md

-- Ajout au Cycle 3 --
Nouvelles modifications apportées par la feature : feature-auto-cycle-3.md

-- Ajout au Cycle 1 --
Nouvelles modifications apportées par la feature : feature-auto-cycle-1.md

-- Ajout au Cycle 2 --
Nouvelles modifications apportées par la feature : feature-auto-cycle-2.md

-- Ajout au Cycle 3 --
Nouvelles modifications apportées par la feature : feature-auto-cycle-3.md

-- Ajout au Cycle 1 --
Nouvelles modifications pour : 

-- Ajout au Cycle 2 --
Nouvelles modifications pour : 

-- Ajout au Cycle 3 --
Nouvelles modifications pour : 

-- Ajout au Cycle 1 --
Nouvelles modifications pour : Complétion du Profil et Spécialisation (Onboarding Homer & Cleaner)

-- Ajout au Cycle 1 --
Nouvelles modifications pour : Moteur de Recherche et Filtrage des Cleaners

-- Ajout au Cycle 2 --
Nouvelles modifications pour : [Réservation d'une Prestation de Ménage]

-- Ajout au Cycle 3 --
Nouvelles modifications pour : Feature Cycle 3

-- Ajout au Cycle 4 --
Nouvelles modifications pour : [Système de Messagerie Interne (In-App Messaging)]

-- Ajout au Cycle 5 --
Nouvelles modifications pour : [Système de Notation et d'Évaluation des Prestations]

-- Ajout au Cycle 6 --
Nouvelles modifications pour : [Système de Paiement Sécurisé et Séquestre des Fonds]

-- Ajout au Cycle 7 --
Nouvelles modifications pour : [Vérification d'Identité (KYC) et Certification des Profils]

-- Ajout au Cycle 8 --
Nouvelles modifications pour : [Planification de Prestations Récurrentes (Abonnement)]

-- Ajout au Cycle 9 --
Nouvelles modifications pour : [Gestion du Calendrier de Disponibilités et des Indisponibilités]

-- Ajout au Cycle 10 --
Nouvelles modifications pour : [Centre de Résolution des Litiges et Gestion des Sinistres]

-- Ajout au Cycle 11 --
Nouvelles modifications pour : [Système de Notifications Centralisé et Alertes Multi-Canal]

-- Ajout au Cycle 12 --
Nouvelles modifications pour : [Génération Automatisée de Factures et Attestations Fiscales]

-- Ajout au Cycle 13 --
Nouvelles modifications pour : [Tableau de Bord Analytique et Suivi des Revenus pour les Cleaners]

-- Ajout au Cycle 14 --
Nouvelles modifications pour : [Système de Parrainage et de Fidélisation (Growth Engine)]

-- Ajout au Cycle 15 --
Nouvelles modifications pour : [Système de Check-in/Check-out et Preuve de Prestation par Photo]

-- Ajout au Cycle 16 --
Nouvelles modifications pour : [Checklist de Prestation Personnalisée et Rapport de Mission]

-- Ajout au Cycle 17 --
Nouvelles modifications pour : Système de Pourboire (Tipping) Post-Prestation

-- Ajout au Cycle 18 --
Nouvelles modifications pour : [Gestion des Modes d'Accès et Protocoles d'Entrée Sécurisés]

-- Ajout au Cycle 19 --
Nouvelles modifications pour : [Gestion Multi-Logements pour les Homers]

-- Ajout au Cycle 20 --
Nouvelles modifications pour : [Gestion des Remplacements et Continuité de Service]

-- Ajout au Cycle 21 --
Nouvelles modifications pour : [Compte Famille et Co-Gestion du Foyer]

-- Ajout au Cycle 22 --
Nouvelles modifications pour : [Gestion de la Logistique Matérielle et des Produits d'Entretien]

-- Ajout au Cycle 23 --
Nouvelles modifications pour : [Système de Réservation Instantanée (Instant Booking) et Missions "Express"]

-- Ajout au Cycle 24 --
Nouvelles modifications pour : [Système de Synchronisation Calendrier Externe (Google/iCal)]

-- Ajout au Cycle 25 --
Nouvelles modifications pour : [Système de Suivi de l'Arrivée du Prestataire en Temps Réel (Live Tracking)]