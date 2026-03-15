I will begin by researching the current data model and business logic to ensure the new feature is perfectly integrated into the existing ecosystem.

Voici le livrable structuré pour la feature **Réservation d'une Prestation de Ménage**, conforme aux standards de l'analyse métier.

# Analyse Métier : Réservation d'une Prestation de Ménage

## 1. Modèle Conceptuel de Données (MCD) mis à jour
Le modèle inclut la relation pivot entre l'Homer et le Cleaner via l'entité `Booking`.

```mermaid
erDiagram
    HOMER ||--o{ BOOKING : "initie"
    CLEANER ||--o{ BOOKING : "reçoit"
    
    HOMER {
        bigint id PK
        varchar address "Obligatoire pour réserver"
        varchar city "Obligatoire pour réserver"
        varchar postal_code "Obligatoire pour réserver"
    }

    CLEANER {
        bigint id PK
        numeric hourly_rate "Base du calcul du prix"
    }

    BOOKING {
        bigint id PK
        date date "Date de l'intervention"
        time start_time "Heure de début"
        time end_time "Heure de fin"
        integer surface "Surface en m2"
        numeric total_price "Calculé : hourly_rate * durée"
        varchar status "Initial : PENDING"
        timestamp created_at
    }
```

## 2. Diagramme de Flux (BPMN)
Ce flux décrit le processus nominal et les contrôles de validité avant la persistance.

```mermaid
graph TD
    A[Homer sur le profil d'un Cleaner] --> B{Connecté & Rôle HOMER?}
    B -- Non --> C[Redirection vers Login]
    B -- Oui --> D{Profil complet?}
    D -- Non --> E[Alerte : Adresse manquante]
    E --> F[Lien vers Edition Profil]
    D -- Oui --> G[Saisie Formulaire Réservation]
    
    G --> H[Calcul dynamique du prix]
    H --> I[Affichage du Total Prévisionnel]
    
    I --> J{Validation par l'Homer}
    J --> K{Cohérence temporelle?}
    
    K -- Erreur --> L[Message : Horaires invalides]
    L --> G
    
    K -- Valide --> M[Création Booking : Status PENDING]
    M --> N[Affichage Confirmation]
```

## 3. Critères d'Acceptation (Gherkin)

### Scénario 1 : Calcul automatique du prix prévisionnel
**Given** un Homer connecté consultant le profil d'un Cleaner ayant un tarif de 20€/h  
**When** l'Homer saisit une intervention de 09:00 à 11:00  
**Then** le système affiche un montant total prévisionnel de 40.00€  

### Scénario 2 : Blocage pour profil incomplet
**Given** un Homer connecté dont l'adresse n'est pas renseignée dans son profil  
**When** il tente d'accéder au formulaire de réservation sur le profil d'un Cleaner  
**Then** le système affiche un message d'erreur indiquant que l'adresse (rue, ville, code postal) est requise pour réserver  
**And** le bouton de validation est désactivé  

### Scénario 3 : Validation de la cohérence temporelle
**Given** un Homer sur le formulaire de réservation  
**When** il saisit une heure de fin (10:00) antérieure ou égale à l'heure de début (11:00)  
**Then** le système empêche la soumission  
**And** un message d'erreur "L'heure de fin doit être postérieure à l'heure de début" s'affiche  

### Scénario 4 : Persistance d'une demande valide
**Given** un Homer avec un profil complet et des données de réservation valides  
**When** il clique sur "Confirmer la réservation"  
**Then** une nouvelle entrée est créée dans la base de données avec le statut "PENDING"  
**And** l'Homer reçoit une confirmation visuelle du succès de sa demande