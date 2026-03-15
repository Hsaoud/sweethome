# Analyse Métier : Complétion du Profil et Spécialisation (Onboarding)

## 1. Modèle Conceptuel de Données (MCD)

```mermaid
erDiagram
    USER ||--o| HOMER_PROFILE : "est un"
    USER ||--o| CLEANER_PROFILE : "est un"
    USER {
        string email
        string password
        enum role "NONE, HOMER, CLEANER"
        float latitude
        float longitude
        string street
        string city
        string zip_code
    }
    HOMER_PROFILE {
        string street
        string city
        string zip_code
    }
    CLEANER_PROFILE {
        float hourly_rate
        text bio
        int action_radius_km
        string city
    }
    CATEGORY {
        string name
        string description
    }
    CLEANER_PROFILE }|--|{ CATEGORY : "propose"
```

## 2. Diagramme de Flux (BPMN)

```mermaid
graph TD
    A[Début Onboarding] --> B{Choix du Rôle}
    
    B -- HOMER --> C[Saisie Adresse Complète]
    C --> D[Validation Champs Obligatoires]
    D --> E[Géocodage de l'adresse]
    E --> F[Enregistrement Profil Homer]
    F --> G[Fin Onboarding]
    
    B -- CLEANER --> H[Saisie Profil: Tarif, Bio, Rayon, Ville]
    H --> I[Sélection Catégories de Service]
    I --> J{Validation Métier}
    
    J -- Invalide --> K[Retour Erreur: Bio < 100 car. ou Tarif <= 0]
    K --> H
    
    J -- Valide --> L[Géocodage de la Ville]
    L --> M[Enregistrement Profil Cleaner]
    M --> G
```

## 3. Critères d'Acceptation

### Scénario 1 : Finalisation du profil Homer (Succès)
**Given** un utilisateur authentifié ayant le rôle "NONE"  
**When** il sélectionne le rôle "HOMER"  
**And** il renseigne une adresse complète (Rue, Ville, Code Postal)  
**Then** le système doit convertir l'adresse en coordonnées GPS (Latitude/Longitude)  
**And** le profil doit être enregistré avec le rôle "HOMER"  

### Scénario 2 : Finalisation du profil Cleaner (Succès)
**Given** un utilisateur authentifié ayant le rôle "NONE"  
**When** il sélectionne le rôle "CLEANER"  
**And** il renseigne un tarif horaire > 0  
**And** il renseigne une biographie de plus de 100 caractères  
**And** il sélectionne au moins une catégorie de service  
**And** il renseigne sa ville et son rayon d'action  
**Then** le système doit convertir la ville en coordonnées GPS (Latitude/Longitude)  
**And** le profil doit être enregistré avec le rôle "CLEANER" et ses spécialisations  

### Scénario 3 : Échec de validation - Biographie Cleaner insuffisante
**Given** un utilisateur en cours d'onboarding "CLEANER"  
**When** il soumet une biographie de moins de 100 caractères  
**Then** le système doit rejeter la soumission  
**And** un message d'erreur doit indiquer que la biographie est trop courte  

### Scénario 4 : Échec de validation - Tarif Cleaner invalide
**Given** un utilisateur en cours d'onboarding "CLEANER"  
**When** il soumet un tarif horaire inférieur ou égal à zéro  
**Then** le système doit rejeter la soumission  
**And** un message d'erreur doit indiquer que le tarif doit être positif  

### Scénario 5 : Échec de validation - Absence de catégorie pour Cleaner
**Given** un utilisateur en cours d'onboarding "CLEANER"  
**When** il ne sélectionne aucune catégorie de service  
**Then** le système doit rejeter la soumission  
**And** un message d'erreur doit exiger la sélection d'au moins une catégorie