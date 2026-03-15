I will read the current database schema to understand the existing entities and how to integrate the new invoice and tax certificate logic.
I will check the feature history to understand how payments were implemented in Cycle 6, which is crucial for the invoice generation logic.
I will search for "payment" in the codebase to find the table definitions and logic related to Cycle 6, which are missing from the `schema.sql` file.
Voici le dossier de spécifications fonctionnelles pour la feature de gestion administrative et fiscale.

```json
{
  "feature": "Génération Automatisée de Factures et Attestations Fiscales",
  "mcd": "erDiagram\n    USER ||--o| HOMER : \"est\"\n    USER ||--o| CLEANER : \"est\"\n    \n    HOMER ||--o{ BOOKING : \"réserve\"\n    CLEANER ||--o{ BOOKING : \"réalise\"\n    \n    BOOKING ||--o{ TRANSACTION : \"génère\"\n    BOOKING ||--o| INVOICE : \"est documenté par\"\n    \n    HOMER ||--o{ TAX_CERTIFICATE : \"reçoit\"\n\n    INVOICE {\n        bigint id PK\n        string invoice_number \"Unique: FAC-YYYY-XXXX\"\n        timestamp issued_at \"Date d'émission\"\n        numeric gross_amount \"Montant payé par le Homer\"\n        numeric commission_amount \"Frais de plateforme\"\n        numeric net_amount \"Revenu net du Cleaner\"\n        string pdf_reference \"Lien sécurisé vers le stockage\"\n        string status \"GENERATED, ARCHIVED\"\n    }\n\n    TAX_CERTIFICATE {\n        bigint id PK\n        int calendar_year \"Année civile concernée\"\n        timestamp generated_at\n        numeric total_eligible_amount \"Somme des montants éligibles\"\n        string pdf_reference \"Lien sécurisé vers le stockage\"\n    }\n\n    TRANSACTION {\n        bigint id PK\n        string type \"PAYMENT (Homer -> Escrow), PAYOUT (Escrow -> Cleaner), REFUND\"\n        string status \"SUCCESS, FAILED, PENDING\"\n        numeric amount\n        timestamp created_at\n    }",
  "bpmn": "graph TD\n    A[Transaction PAYOUT réussie] --> B[Calcul des données financières]\n    B --> C[Récupération des coordonnées des parties]\n    C --> D[Génération du PDF de la facture]\n    D --> E[Stockage sécurisé du PDF]\n    E --> F[Création de l'entrée INVOICE en BDD]\n    F --> G[Notification des parties]\n\n    H[Homer demande l'attestation fiscale de l'année N-1] --> I{Attestation déjà existante ?}\n    I -- Oui --> J[Téléchargement direct]\n    I -- Non --> K[Agrégation des INVOICES payées sur l'année N-1]\n    K --> L[Génération du PDF de l'attestation]\n    L --> M[Stockage sécurisé et lien à l'Homer]\n    M --> J",
  "acceptance_criteria": [
    {
      "id": "AC1",
      "title": "Génération automatique de facture au déblocage des fonds",
      "scenario": "Given un Booking avec le statut 'COMPLETED' et une Transaction 'PAYOUT' en statut 'SUCCESS'\nWhen le système détecte la confirmation du virement vers le Cleaner\nThen une Invoice doit être générée automatiquement au format PDF\nAnd l'Invoice doit être liée au Booking original."
    },
    {
      "id": "AC2",
      "title": "Contenu obligatoire de la facture",
      "scenario": "Given une Invoice générée\nThen elle doit afficher les noms et adresses du Homer et du Cleaner\nAnd elle doit mentionner la date de prestation, la durée (heures) et le montant total\nAnd elle doit détailler la commission de la plateforme et le montant net perçu par le prestataire."
    },
    {
      "id": "AC3",
      "title": "Attestation fiscale annuelle pour le Homer",
      "scenario": "Given un Homer ayant eu des prestations payées l'année civile précédente\nWhen le Homer clique sur 'Télécharger mon attestation fiscale'\nThen le système génère un document PDF regroupant toutes les prestations éligibles de l'année précédente\nAnd le montant total éligible au crédit d'impôt doit y figurer clairement."
    },
    {
      "id": "AC4",
      "title": "Tableau de bord financier du Cleaner",
      "scenario": "Given un Cleaner connecté sur son dashboard\nThen il doit pouvoir consulter un graphique de l'évolution de ses revenus (net)\nAnd il doit pouvoir filtrer par période (Mois/Année)\nAnd il doit pouvoir télécharger en masse ses factures sur une période donnée."
    },
    {
      "id": "AC5",
      "title": "Pérennité des documents administratifs",
      "scenario": "Given une Invoice générée entre un Homer et un Cleaner\nWhen le profil du Cleaner est désactivé ou supprimé\nThen le Homer doit toujours pouvoir accéder et télécharger la facture historique depuis son propre dashboard."
    }
  ]
}
```