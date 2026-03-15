I will read the current database schema and existing documentation to understand the baseline for the Conceptual Data Model (MCD) and the business context.
Voici le dossier de conception métier pour la feature **Moteur de Recherche et Filtrage des Cleaners**.

```json
{
  "feature": "Moteur de Recherche et Filtrage des Cleaners",
  "mcd": "```mermaid\nerDiagram\n    USERS ||--o| CLEANERS : \"est un (héritage)\"\n    CLEANERS ||--o{ CLEANER_CATEGORIES : \"propose\"\n    CATEGORIES ||--o{ CLEANER_CATEGORIES : \"est classé dans\"\n    USERS ||--o{ REVIEWS : \"reçoit (reviewee_id)\"\n\n    USERS {\n        bigint id PK\n        varchar first_name\n        varchar profile_image\n    }\n\n    CLEANERS {\n        bigint id PK, FK\n        boolean available\n        varchar city\n        numeric hourly_rate\n        integer experience_years\n    }\n\n    CATEGORIES {\n        bigint id PK\n        varchar name\n    }\n\n    CLEANER_CATEGORIES {\n        bigint cleaner_id PK, FK\n        bigint category_id PK, FK\n    }\n\n    REVIEWS {\n        bigint id PK\n        integer rating\n        bigint reviewee_id FK\n    }\n```",
  "bpmn": "```mermaid\nprogessView\n    graph TD\n        Start((Besoin de ménage)) --> InputCity[Saisir la ville]\n        InputCity --> FilterOpt{Filtres Optionnels ?}\n        \n        FilterOpt -- Oui --> SelectCat[Sélectionner Catégories]\n        SelectCat --> SelectSort[Choisir le Tri]\n        \n        FilterOpt -- Non --> DefaultSort[Tri par Prix Croissant]\n        DefaultSort --> Query[Exécuter la Recherche]\n        SelectSort --> Query\n\n        Query --> CheckResults{Résultats ?}\n        \n        CheckResults -- Non --> EmptyMsg[Afficher 'Aucun Cleaner disponible...']\n        CheckResults -- Oui --> CalcRating[Calculer la note moyenne par Cleaner]\n        CalcRating --> DisplayList[Afficher la liste des Cleaners]\n        \n        DisplayList --> End((Résultats affichés))\n        EmptyMsg --> End\n```",
  "acceptance_criteria": [
    {
      "id": "AC1",
      "title": "Recherche par ville",
      "scenario": "Given un Homer sur la page de recherche, When il saisit une ville existante (ex: 'Paris'), Then le système affiche uniquement les Cleaners dont le champ 'city' correspond exactement à la saisie."
    },
    {
      "id": "AC2",
      "title": "Filtrage par catégories",
      "scenario": "Given une liste de résultats affichée, When le Homer sélectionne une catégorie (ex: 'Vitre'), Then le système n'affiche que les Cleaners possédant cette catégorie dans 'cleaner_categories'."
    },
    {
      "id": "AC3",
      "title": "Condition de disponibilité",
      "scenario": "Given n'importe quelle recherche, When le système récupère les prestataires, Then il doit exclure automatiquement tous les Cleaners ayant 'available = false'."
    },
    {
      "id": "AC4",
      "title": "Tri par défaut (Tarif)",
      "scenario": "Given une recherche sans option de tri spécifique, When les résultats sont affichés, Then ils sont ordonnés par 'hourly_rate' du plus petit au plus grand."
    },
    {
      "id": "AC5",
      "title": "Option de tri par expérience",
      "scenario": "Given une liste de résultats, When le Homer sélectionne le tri par expérience, Then le système réordonne la liste par 'experience_years' décroissant."
    },
    {
      "id": "AC6",
      "title": "Calcul de la note moyenne",
      "scenario": "Given un Cleaner ayant reçu plusieurs notes (ex: 4 et 5) dans la table 'reviews', When il apparaît dans les résultats, Then sa carte affiche la moyenne arrondie à une décimale (ex: 4.5)."
    },
    {
      "id": "AC7",
      "title": "Gestion des cas vides",
      "scenario": "Given une ville ou un filtre sans correspondance (ex: 'VilleInexistante'), When la recherche est lancée, Then le message 'Aucun Cleaner disponible pour cette recherche' est affiché à la place de la liste."
    }
  ]
}
```