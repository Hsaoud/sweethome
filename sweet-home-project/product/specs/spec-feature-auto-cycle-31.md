Voici le dossier de conception fonctionnelle pour la feature **[Optimisation de Tournée et Calcul d'Itinéraire pour les Cleaners]**.

### 1. Modèle Conceptuel de Données (MCD)

Ce modèle définit les entités et relations nécessaires pour supporter l'intelligence logistique et le suivi des itinéraires sans présumer de l'implémentation technique.

```mermaid
erDiagram
    CLEANER ||--o{ DAILY_ROUTE : "effectue"
    DAILY_ROUTE ||--|{ ROUTE_STEP : "est composee de"
    BOOKING ||--o| ROUTE_STEP : "definit"
    
    CLEANER {
        string id
        string vehicule_prefere
    }

    DAILY_ROUTE {
        date date_itineraire
        datetime derniere_optimisation
        string statut_calcul
    }

    ROUTE_STEP {
        int ordre_sequence
        datetime heure_arrivee_estimee
        datetime heure_depart_estimee
        int duree_trajet_prevue_min
        float distance_km
        string statut_etape
    }

    BOOKING {
        string id
        string adresse_geolocalisee
        datetime heure_debut_prevue
        datetime heure_fin_prevue
        string statut_mission
    }
```

### 2. Diagramme de flux (BPMN)

Ce diagramme illustre le cycle de vie d'une tournée, de son calcul à l'exécution avec gestion des aléas.

```mermaid
graph TD
    A[Missions confirmées pour J] --> B[Calculer itinéraire optimal via API Cartographique]
    B --> C{Itinéraire validé ?}
    C -- Oui --> D[Générer la vue 'Ma Journée']
    C -- Non --> B
    
    D --> E[Cleaner consulte sa Timeline]
    E --> F[Lancer le guidage vers l'étape N]
    F --> G[Ouverture App Navigation externe]
    
    subgraph Surveillance_Temps_Reel
    H[Surveiller Trafic & Position] --> I{Retard > 15min détecté ?}
    I -- Oui --> J[Calculer nouvel ETA]
    J --> K[Notifier le Homer de l'étape N+1]
    I -- Non --> H
    end
    
    G --> L[Arrivée à la Mission]
    L --> M[Mission terminée]
    M --> H
```

### 3. Critères d'Acceptation (Gherkin)

**Scénario 1 : Calcul automatique de l'itinéraire optimal**
*   **Given** un Cleaner ayant au moins 2 missions confirmées pour la même journée.
*   **When** le Cleaner accède à l'écran "Ma Journée".
*   **Then** le système doit calculer l'ordre de passage minimisant le temps de trajet total.
*   **And** afficher les étapes numérotées sur une carte interactive.

**Scénario 2 : Consultation de la Timeline "Ma Journée"**
*   **Given** une tournée calculée pour un Cleaner.
*   **When** le Cleaner visualise sa timeline.
*   **Then** il doit voir pour chaque mission : l'heure de départ prévue de sa position actuelle, la durée estimée du trajet (trafic inclus) et l'heure d'arrivée estimée (ETA).

**Scénario 3 : Notification prédictive de retard**
*   **Given** un Cleaner en déplacement entre la Mission A et la Mission B.
*   **When** les conditions de trafic réel augmentent le temps de trajet de telle sorte que l'ETA dépasse de plus de 10 minutes l'heure de début prévue de la Mission B.
*   **Then** une notification automatique est envoyée au Client (Homer) de la Mission B pour l'informer du nouvel horaire d'arrivée.

**Scénario 4 : Lancement du guidage externe**
*   **Given** une mission sélectionnée dans la timeline.
*   **When** le Cleaner appuie sur le bouton "Lancer le guidage".
*   **Then** l'application doit proposer le choix entre les applications de navigation installées (Waze, Google Maps, Apple Maps).
*   **And** transmettre les coordonnées GPS exactes de la destination à l'application choisie.

**Scénario 5 : Mise à jour dynamique de la tournée**
*   **Given** une tournée en cours d'exécution.
*   **When** une nouvelle mission est confirmée en "dernière minute" pour la même journée.
*   **Then** le système doit recalculer instantanément l'itinéraire optimal intégrant ce nouveau point d'arrêt.