# Analyse Métier : Checklist de Prestation Personnalisée et Rapport de Mission

## 1. Modèle Conceptuel de Données (MCD)

```mermaid
erDiagram
    BOOKING ||--o{ CHECKLIST_ITEM : "contient"
    TASK_TEMPLATE }|--|| TASK_CATEGORY : "appartient à"
    CHECKLIST_ITEM }|--|| TASK_CATEGORY : "catégorisé par"
    BOOKING ||--o| MISSION_REPORT : "génère"
    CHECKLIST_ITEM ||--o| PHOTO_EVIDENCE : "peut exiger"

    TASK_TEMPLATE {
        string description
        boolean default_critical
    }

    TASK_CATEGORY {
        string label
    }

    CHECKLIST_ITEM {
        string description
        boolean is_critical
        enum status "PENDING, COMPLETED, SKIPPED"
        string comment
        string photo_url
    }

    MISSION_REPORT {
        datetime generated_at
        string pdf_storage_path
        string status "DRAFT, SENT"
    }
```

---

## 2. Diagramme de flux (BPMN)

```mermaid
graph TD
    %% Phase de Planification
    A[Homer: Création de Réservation] --> B{Utiliser Bibliothèque?}
    B -- Oui --> C[Sélectionner tâches standards par Catégorie]
    B -- Non --> D[Saisir tâches personnalisées]
    C --> E[Définir tâches comme 'Critiques']
    D --> E
    E --> F[Validation de la Checklist par le Homer]

    %% Phase d'Exécution
    F --> G[Cleaner: Check-in sur site]
    G --> H[Cleaner: Parcours de la Checklist]
    H --> I{Tâche Critique?}
    I -- Oui --> J[Prendre Photo Obligatoire]
    I -- Non --> K[Valider/Commenter la tâche]
    J --> K
    K --> L{Dernière tâche?}
    L -- Non --> H
    L -- Oui --> M[Tentative de Check-out]

    %% Phase de Clôture
    M --> N{Checklist 100% traitée?}
    N -- Non --> O[Erreur: Traitement requis]
    O --> H
    N -- Oui --> P[Validation Check-out]
    P --> Q[Système: Génération Rapport PDF]
    Q --> R[Envoi Automatique au Homer]
    R --> S[Fin de Mission]
```

---

## 3. Critères d'Acceptation (Gherkin)

### Scénario 1 : Configuration de la checklist par le Homer
**Given** que je suis un Homer en train de créer une réservation
**When** j'accède à la section "Détails de la prestation"
**Then** le système me propose une liste de tâches pré-définies classées par catégories (Cuisine, Salon, etc.)
**And** je peux ajouter une tâche libre avec une description personnalisée
**And** je peux marquer n'importe quelle tâche comme "Critique"

### Scénario 2 : Obligation de traitement avant Check-out
**Given** que je suis un Cleaner ayant effectué le Check-in
**When** je tente de valider mon "Check-out"
**But** qu'au moins une tâche de la checklist n'a été ni validée ni commentée
**Then** le système bloque la clôture de la mission
**And** m'affiche un message d'erreur indiquant les points restants à traiter

### Scénario 3 : Preuve photo pour tâche critique
**Given** qu'une tâche a été marquée comme "Critique" par le Homer
**When** je sélectionne cette tâche pour la valider dans mon interface Cleaner
**Then** le système doit m'imposer la prise d'une photo via l'application
**And** la validation de la tâche est impossible sans l'ajout de ce média

### Scénario 4 : Génération et transmission du rapport de mission
**Given** qu'une prestation vient d'être clôturée par le "Check-out" du Cleaner
**When** le système finalise la transaction
**Then** un rapport PDF est généré incluant :
  - Le statut de chaque tâche (Validée / Ignorée avec commentaire)
  - Les photos associées aux tâches critiques
  - L'heure précise de début et de fin d'intervention
**And** ce rapport est envoyé par email au Homer et devient consultable dans son historique de réservation