Voici le livrable structuré pour la feature **Tableau de Bord Analytique et Suivi des Revenus pour les Cleaners**.

---

### 1. Modèle Conceptuel de Données (MCD) mis à jour

Ce modèle intègre les dimensions nécessaires à l'analyse (temporelle, géographique, catégorielle) et les indicateurs financiers issus des cycles précédents.

```mermaid
erDiagram
    CLEANER ||--o{ BOOKING : "réalise"
    CLEANER ||--o{ RECURRING_SUBSCRIPTION : "gère"
    BOOKING ||--o| REVIEW : "reçoit"
    BOOKING }|--|| CATEGORY : "concerne"
    BOOKING ||--|| FINANCIAL_RECORD : "génère"
    HOMER ||--o{ BOOKING : "réserve"

    CLEANER {
        bigint id
        string service_area
        decimal average_rating
    }

    BOOKING {
        bigint id
        date date
        string status
        decimal total_price
        decimal commission_amount
        decimal net_amount
        string city_zone
    }

    FINANCIAL_RECORD {
        bigint id
        datetime processed_at
        decimal gross_amount
        decimal net_amount
        string transaction_type
    }

    RECURRING_SUBSCRIPTION {
        bigint id
        string frequency
        decimal projected_amount
        boolean is_active
        date next_occurrence
    }

    REVIEW {
        bigint id
        int rating
        datetime created_at
    }

    CATEGORY {
        bigint id
        string name
    }
```

---

### 2. Diagramme de flux BPMN (Processus de Consultation et d'Analyse)

Ce flux décrit le parcours utilisateur du Cleaner pour piloter son activité.

```mermaid
graph TD
    Start((Début)) --> Auth[Authentification Cleaner]
    Auth --> AccessDash[Accéder au Tableau de Bord]
    
    subgraph "Agrégation des Données"
        AccessDash --> LoadFin[Calcul CA Brut/Net]
        LoadFin --> LoadKPI[Calcul Taux de Complétion]
        LoadKPI --> LoadSat[Calcul Évolution Notes]
        LoadSat --> LoadProj[Calcul Projections Financières]
    end

    LoadProj --> Display[Afficher les Graphiques et Indicateurs]
    
    Display --> UserAction{Action Utilisateur}
    
    UserAction -- "Filtrer Période" --> Recompute[Mise à jour dynamique des données]
    Recompute --> Display
    
    UserAction -- "Analyser Demande" --> ShowHeatMap[Afficher Zones/Catégories Rentables]
    ShowHeatMap --> Display
    
    UserAction -- "Exporter" --> GenExport[Générer CSV/Excel]
    GenExport --> Download[Téléchargement du document]
    Download --> End((Fin))
    
    UserAction -- "Quitter" --> End
```

---

### 3. Critères d'Acceptation (Gherkin)

#### Scénario 1 : Synthèse financière et filtrage temporel
**Etant donné** que je suis un Cleaner connecté avec des prestations passées
**Quand** j'accède à mon tableau de bord analytique
**Et que** je sélectionne une période (ex: "Mois dernier")
**Alors** le système affiche le Chiffre d'Affaires Brut total
**Et** le montant net après déduction des commissions de la plateforme
**Et** la variation par rapport à la période précédente.

#### Scénario 2 : Suivi des indicateurs de performance (KPIs) et satisfaction
**Etant donné** que je consulte mes statistiques de performance
**Alors** le système doit calculer le taux de complétion (Prestations honorées / Total des réservations acceptées)
**Et** afficher un graphique d'évolution de ma note moyenne sur les 12 derniers mois
**Et** indiquer le nombre total de commentaires reçus.

#### Scénario 3 : Analyse de la demande et rentabilité
**Etant donné** que je souhaite optimiser mes déplacements
**Quand** je consulte la section "Analyse de la demande"
**Alors** le système affiche la répartition du CA par catégorie de service (ex: 60% Ménage standard, 40% Repassage)
**Et** identifie les zones géographiques (villes ou quartiers) générant le plus de revenus.

#### Scénario 4 : Projections financières (Revenus à venir)
**Etant donné** que j'ai des réservations confirmées pour le futur et des abonnements actifs
**Alors** le système doit afficher une estimation des revenus "sécurisés" pour les 30 prochains jours
**Et** distinguer les revenus issus de prestations ponctuelles des revenus issus d'abonnements récurrents.

#### Scénario 5 : Exportation des données comptables
**Etant donné** que je suis sur le tableau de bord avec un filtre actif
**Quand** je clique sur le bouton "Exporter les données"
**Alors** le système génère un fichier (CSV ou Excel) contenant le détail de chaque transaction (Date, Client, Service, Brut, Commission, Net)
**Et** le téléchargement démarre automatiquement.