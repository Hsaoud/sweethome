I will begin by researching the current state of the database schema and architecture to ensure the new multi-property management logic integrates seamlessly with the existing system.

Voici le dossier de conception fonctionnelle pour la feature **Gestion Multi-Logements pour les Homers**.

```json
{
  "feature": "Gestion Multi-Logements pour les Homers",
  "version": "1.0",
  "status": "DRAFT",
  "conceptual_data_model": "mermaid\nerDiagram\n    homers ||--o{ properties : \"possède\"\n    properties ||--o{ bookings : \"est le lieu de\"\n    \n    homers {\n        bigint id PK\n    }\n\n    properties {\n        bigint id PK\n        bigint homer_id FK\n        varchar alias \"Ex: Ma Maison, Airbnb\"\n        varchar address\n        varchar city\n        varchar postal_code\n        varchar floor \"Étage\"\n        varchar access_code \"Code d'entrée\"\n        text security_instructions \"Consignes de sécurité\"\n        boolean is_default \"Propriété par défaut\"\n        timestamp created_at\n        timestamp updated_at\n    }\n\n    bookings {\n        bigint id PK\n        bigint property_id FK\n        bigint cleaner_id FK\n        date date\n        time start_time\n        varchar status\n    }\n",
  "business_process_diagram": "mermaid\n\n    flowchart TD\n    Start([Début du tunnel de réservation]) --> CheckProps{Le Homer a-t-il des propriétés ?}\n    \n    CheckProps -- Non --> AddProp[Saisie obligatoire d'une première propriété]\n    CheckProps -- Oui --> ShowProps[Affichage de la liste des propriétés]\n    \n    AddProp --> SaveProp[Enregistrement de la propriété]\n    SaveProp --> SelectProp[Sélection de la propriété pour la prestation]\n    \n    ShowProps --> SelectProp\n    SelectProp --> SearchCleaner[Recherche de cleaners dans la zone géographique]\n    \n    SearchCleaner --> BookingProcess[Suite du processus de réservation classique]\n    \n    BookingProcess --> End([Prestation associée à l'adresse spécifique])\n\n    subgraph Gestion_Profil\n    Manage[Accès 'Mes Logements'] --> List[Lister les propriétés]\n    List --> Edit[Modifier/Supprimer une propriété]\n    List --> SetDefault[Définir comme adresse par défaut]\n    end\n",
  "acceptance_criteria": [
    {
      "id": "AC_01",
      "title": "Ajout d'une nouvelle propriété",
      "scenario": "Un Homer souhaite ajouter une résidence secondaire",
      "given": "un Homer connecté sur son tableau de bord",
      "when": "il saisit un alias (ex: 'Maison de campagne'), une adresse complète, l'étage, un code d'accès et des consignes",
      "then": "la propriété est enregistrée et rattachée à son compte unique"
    },
    {
      "id": "AC_02",
      "title": "Sélection de propriété lors de la réservation",
      "scenario": "Un Homer possède 3 logements et veut réserver pour l'un d'eux",
      "given": "un Homer ayant enregistré 'Appartement Paris' et 'Studio Lyon'",
      "when": "il initialise une nouvelle réservation",
      "then": "le système lui propose de choisir entre ses deux logements avant de filtrer les cleaners disponibles"
    },
    {
      "id": "AC_03",
      "title": "Gestion de la propriété par défaut",
      "scenario": "Accélération du tunnel pour l'adresse principale",
      "given": "un Homer ayant défini 'Ma Maison' comme propriété par défaut",
      "when": "il accède au tunnel de réservation",
      "then": "'Ma Maison' est pré-sélectionnée automatiquement mais reste modifiable"
    },
    {
      "id": "AC_04",
      "title": "Exactitude des documents légaux",
      "scenario": "Génération d'une facture pour une prestation spécifique",
      "given": "une prestation terminée effectuée à l'adresse '12 rue des Fleurs, Paris'",
      "when": "la facture ou l'attestation fiscale est générée",
      "then": "l'adresse mentionnée sur le document doit être celle de la propriété choisie lors de la réservation et non l'adresse de facturation du compte"
    }
  ]
}
```