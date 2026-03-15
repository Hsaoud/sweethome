I will now analyze the existing architecture and database state to ensure the new business requirements for "Compte Famille et Co-Gestion du Foyer" are accurately integrated.
I will now read the current database state to understand the existing data model and how to integrate the new household co-management features.
I will now check the specifications for Cycle 19 to understand the multi-lodging management implementation, which serves as the foundation for the household co-management feature in Cycle 21.
I will now check the messaging system specifications from Cycle 4 to understand how it's currently structured and determine the best way to implement group messaging for households and cleaners.
I will now check the notification system specifications from Cycle 11 to understand how to incorporate co-homers into the existing notification flow and ensure they receive critical updates for their households.
Voici la conception métier pour la feature **Compte Famille et Co-Gestion du Foyer (Cycle 21)**.

```json
{
  "feature": "Compte Famille et Co-Gestion du Foyer",
  "version": "1.0",
  "status": "VALIDATED",
  "conceptual_data_model": "mermaid\nerDiagram\n    properties ||--o{ property_members : \"est partagée avec\"\n    properties ||--o{ property_invitations : \"fait l'objet de\"\n    users ||--o{ property_members : \"est membre de\"\n    \n    properties {\n        bigint id PK\n        bigint homer_id FK \"Titulaire (Homer Principal)\"\n        varchar alias\n    }\n\n    property_members {\n        bigint id PK\n        bigint property_id FK\n        bigint user_id FK\n        timestamp joined_at\n        boolean is_active \"Défaut: true\"\n    }\n\n    property_invitations {\n        bigint id PK\n        bigint property_id FK\n        varchar email \"Email de l'invité\"\n        varchar token UK \"Token unique d'invitation\"\n        varchar status \"PENDING, ACCEPTED, EXPIRED, REVOKED\"\n        timestamp created_at\n        timestamp expires_at\n    }\n\n    bookings {\n        bigint id PK\n        bigint property_id FK\n    }\n\n    messages {\n        bigint id PK\n        bigint booking_id FK\n        bigint sender_id FK\n    }\n",
  "business_process_diagram": "mermaid\n\nflowchart TD\n    subgraph Invitation\n    A[Homer Titulaire] -->|Saisit Email| B{Déjà 3 Co-Homers?}\n    B -- Oui --> C[Erreur: Limite atteinte]\n    B -- Non --> D[Générer Token & Expire_at]\n    D --> E[Envoi Email Invitation]\n    E --> F[Statut: PENDING]\n    end\n\n    subgraph Onboarding_Co_Homer\n    G[Invité clique sur le lien] --> H{Compte existant ?}\n    H -- Non --> I[Inscription simplifiée]\n    H -- Oui --> J[Connexion]\n    I --> K[Acceptation Invitation]\n    J --> K\n    K --> L[Création PropertyMember]\n    L --> M[Accès au Dashboard Partagé]\n    end\n\n    subgraph Collaboration_Operationnelle\n    N[Événement Booking/Message] --> O[Identifier Propriété cible]\n    O --> P[Récupérer Titulaire + Co-Homers actifs]\n    P --> Q[Diffusion Notification Multi-Canal]\n    P --> R[Accès visuel au fil de discussion groupe]\n    end\n\n    subgraph Securite_Financiere\n    S[Accès Factures/Paiement] --> T{Est Titulaire ?}\n    T -- Non --> U[Accès Refusé]\n    T -- Oui --> V[Visualisation & Règlement]\n    end\n",
  "acceptance_criteria": [
    {
      "id": "AC_01",
      "title": "Limitation du nombre de Co-Homers",
      "scenario": "Tentative d'invitation au-delà de la limite",
      "given": "un Homer titulaire ayant déjà 3 Co-Homers actifs rattachés à sa 'Résidence Principale'",
      "when": "il tente d'inviter une 4ème personne via son email",
      "then": "le système bloque l'action et affiche un message d'erreur : 'Limite de 3 co-habitants atteinte pour ce logement.'"
    },
    {
      "id": "AC_02",
      "title": "Accès collaboratif au calendrier et rapports",
      "scenario": "Consultation par un Co-Homer",
      "given": "un Co-Homer 'B' rattaché à la propriété 'P' de l'Homer 'A'",
      "when": "il se connecte à son espace",
      "then": "il doit voir toutes les prestations passées et futures liées à 'P'",
      "and": "il peut consulter les checklists et photos de fin de mission au même titre que le titulaire."
    },
    {
      "id": "AC_03",
      "title": "Messagerie de Groupe Automatique",
      "scenario": "Échange sur une prestation en cours",
      "given": "une réservation pour une propriété ayant 1 Titulaire, 2 Co-Homers et 1 Cleaner",
      "when": "le Cleaner envoie un message dans le fil de discussion de la réservation",
      "then": "le Titulaire ET les 2 Co-Homers reçoivent une notification de message",
      "and": "n'importe lequel des 3 membres du foyer peut répondre au Cleaner dans le même fil."
    },
    {
      "id": "AC_04",
      "title": "Isolation des privilèges financiers",
      "scenario": "Tentative d'accès à la facturation par un Co-Homer",
      "given": "un Co-Homer connecté",
      "when": "il tente d'accéder à l'onglet 'Facturation' ou de modifier le moyen de paiement",
      "then": "l'accès lui est refusé (menu invisible ou erreur 403)",
      "and": "le système précise que seul le titulaire du compte peut gérer les aspects financiers."
    },
    {
      "id": "AC_05",
      "title": "Révocation d'un accès",
      "scenario": "Suppression d'un Co-Homer",
      "given": "un Co-Homer 'B' ayant accès à la propriété 'P'",
      "when": "le Homer titulaire supprime le membre 'B' depuis son interface de gestion",
      "then": "le lien dans `property_members` passe en `is_active = false` (ou supprimé)",
      "and": "le membre 'B' perd instantanément l'accès au calendrier, à la messagerie de groupe et aux notifications de cette propriété."
    }
  ]
}
```