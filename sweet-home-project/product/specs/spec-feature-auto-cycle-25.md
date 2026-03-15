# Système de Suivi de l'Arrivée du Prestataire en Temps Réel (Live Tracking)

## 1. Modèle Conceptuel de Données (MCD)

```mermaid
erDiagram
    BOOKING ||--o| TRACKING_SESSION : "est suivie par"
    CLEANER ||--o{ TRACKING_SESSION : "active"
    TRACKING_SESSION ||--o{ GPS_LOG : "enregistre"
    CLEANER ||--|| TRANSPARENCY_SCORE : "influence"

    BOOKING {
        datetime scheduled_start
        string status
    }

    TRACKING_SESSION {
        id uuid
        datetime activated_at
        datetime deactivated_at
        boolean is_manual_opt_out
        enum status "ACTIVE, COMPLETED, CANCELLED"
    }

    GPS_LOG {
        float latitude
        float longitude
        datetime timestamp
    }

    TRANSPARENCY_SCORE {
        float current_rating
        int total_trackable_missions
        int successful_tracking_missions
    }
```

## 2. Diagramme de flux (BPMN)

```mermaid
graph TD
    A[T-30 min avant début prestation] --> B{Statut Réservation?}
    B -- Confirmée --> C[Activation auto du partage GPS]
    B -- Autre --> Z[Fin de processus]
    
    C --> D[Carte visible sur Dashboard Homer]
    D --> E[Récupération coordonnées Cleaner]
    
    E --> F{Distance < 500m?}
    F -- Oui --> G[Envoi Notification 'Arrivée Imminente']
    F -- Non --> E
    
    G --> H{Check-in validé?}
    
    E --> I{Cleaner désactive manuellement?}
    I -- Oui --> J[Maj Indicateur Transparence]
    J --> K[Désactivation Partage GPS]
    K --> L[Masquer Carte Homer]
    
    H -- Oui --> K
    H -- Non --> E
    
    L --> Z
```

## 3. Critères d'Acceptation (Gherkin)

### Scénario 1 : Activation automatique du suivi
**Given** une réservation confirmée prévue à 14h00
**And** il est actuellement 13h30 (T-30 min)
**When** le système déclenche la fenêtre de suivi
**Then** la session de tracking doit passer au statut "ACTIVE"
**And** l'Homer doit voir apparaître le composant "Live Tracking" sur son tableau de bord.

### Scénario 2 : Notification de proximité (Geofencing)
**Given** une session de tracking active pour un Cleaner
**And** la position GPS du Cleaner est à plus de 500 mètres du domicile de l'Homer
**When** les coordonnées GPS du Cleaner entrent dans un rayon inférieur ou égal à 500 mètres
**Then** une notification push "Arrivée Imminente" est envoyée instantanément à l'Homer.

### Scénario 3 : Fin de suivi sur Check-in
**Given** une session de tracking active
**When** le Cleaner valide son "Check-in" via l'application pour débuter sa mission
**Then** la session de tracking doit passer immédiatement au statut "COMPLETED"
**And** l'accès aux coordonnées GPS du Cleaner doit être révoqué
**And** la carte doit disparaître du tableau de bord de l'Homer.

### Scénario 4 : Désactivation manuelle et impact sur la transparence
**Given** une session de tracking active
**When** le Cleaner choisit de désactiver manuellement le partage de position
**Then** le flux de données GPS s'interrompt immédiatement
**And** le système doit décrémenter le ratio de missions suivies dans le "Transparency Score" du Cleaner.