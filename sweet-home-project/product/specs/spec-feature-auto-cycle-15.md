I will begin by examining the current database schema and architectural documentation to ensure the updated Conceptual Data Model (MCD) is consistent with the existing system.
I will now check the backlog files for cycles 6 and 10 to see if any database modifications were made that could impact the current feature's design.
```json
{
  "feature": "Système de Check-in/Check-out et Preuve de Prestation par Photo",
  "conceptual_data_model": {
    "description": "Mise à jour du modèle pour inclure les horodatages de prestation et les preuves visuelles.",
    "mermaid": "erDiagram\n    bookings {\n        bigint id PK\n        timestamp check_in_at \"Horodatage immuable du début\"\n        timestamp check_out_at \"Horodatage immuable de la fin\"\n        varchar status \"Statuts ajoutés: IN_PROGRESS, COMPLETED\"\n    }\n\n    service_proofs {\n        bigint id PK\n        bigint booking_id FK\n        varchar photo_url \"Lien vers le stockage S3/Cloud\"\n        timestamp uploaded_at\n    }\n\n    bookings ||--o{ service_proofs : \"est documenté par\"\n    homers ||--o{ bookings : \"réserve\"\n    cleaners ||--o{ bookings : \"réalise\""
  },
  "business_process_model": {
    "description": "Flux opérationnel sécurisé par géofencing et preuves visuelles.",
    "mermaid": "graph TD\n    A[Arrivée du Cleaner] --> B{Check-in demandé}\n    B --> C{Géolocalisation < 200m?}\n    C -- Non --> D[Erreur: Trop éloigné de l'adresse]\n    C -- Oui --> E[Enregistrement check_in_at]\n    E --> F[Statut Booking = IN_PROGRESS]\n    F --> G[Réalisation de la prestation]\n    G --> H[Check-out demandé]\n    H --> I{Min. 3 photos uploadées?}\n    I -- Non --> J[Erreur: Photos obligatoires]\n    I -- Oui --> K[Enregistrement check_out_at]\n    K --> L[Sauvegarde des ServiceProofs]\n    L --> M[Statut Booking = COMPLETED]\n    M --> N[Notification Homer avec lien photos]\n    N --> O[Fin de mission]"
  },
  "acceptance_criteria": [
    {
      "id": "AC_01",
      "title": "Validation du Check-in par géofencing",
      "scenario": "Given un Cleaner avec une réservation au statut 'PAID_ESCROW'\nAnd l'adresse du Homer située à 150m de la position GPS du Cleaner\nWhen le Cleaner actionne 'Démarrer la prestation'\nThen le système enregistre la date/heure actuelle dans 'check_in_at'\nAnd le statut de la réservation passe à 'IN_PROGRESS'"
    },
    {
      "id": "AC_02",
      "title": "Refus du Check-in hors périmètre",
      "scenario": "Given un Cleaner avec une réservation au statut 'PAID_ESCROW'\nAnd l'adresse du Homer située à 500m de la position GPS du Cleaner\nWhen le Cleaner actionne 'Démarrer la prestation'\nThen le système bloque l'action\nAnd affiche un message d'erreur 'Vous devez être sur place pour démarrer'"
    },
    {
      "id": "AC_03",
      "title": "Obligation de preuves photos pour le Check-out",
      "scenario": "Given une réservation au statut 'IN_PROGRESS'\nWhen le Cleaner tente de valider le 'Check-out' avec seulement 2 photos\nThen le système refuse la validation\nAnd demande au minimum 3 photos pour clôturer"
    },
    {
      "id": "AC_04",
      "title": "Clôture et notification après Check-out",
      "scenario": "Given une réservation au statut 'IN_PROGRESS'\nWhen le Cleaner valide le 'Check-out' avec 3 photos valides\nThen le système enregistre 'check_out_at'\nAnd le statut passe à 'COMPLETED'\nAnd l'Homer reçoit une notification contenant l'accès aux photos"
    },
    {
      "id": "AC_05",
      "title": "Immuabilité des données de prestation",
      "scenario": "Given une prestation terminée ('COMPLETED')\nWhen une tentative de modification manuelle des champs 'check_in_at' ou 'check_out_at' survient\nThen le système rejette la modification (champs en lecture seule après validation)"
    }
  ]
}
```