L'implémentation du **Système de Parrainage et de Fidélisation (Growth Engine)** nécessite une extension du modèle de données pour intégrer la gestion des portefeuilles virtuels, des codes de parrainage et des promotions administratives.

Voici l'analyse métier structurée pour cette feature.

### 1. Modèle Conceptuel de Données (MCD)

Ce modèle introduit les entités `ReferralCode`, `Promotion`, `Wallet` et `WalletTransaction` pour supporter la logique de parrainage et de crédit.

```mermaid
erDiagram
    USER ||--|| REFERRAL_CODE : "possède"
    USER ||--|| WALLET : "détient"
    USER ||--o{ REFERRAL : "est parrain de"
    USER ||--o{ REFERRAL : "est filleul de"
    
    REFERRAL_CODE {
        string code PK
        datetime created_at
        boolean is_active
    }

    WALLET {
        uuid id PK
        decimal balance
        string currency
        datetime last_updated
    }

    WALLET_TRANSACTION {
        uuid id PK
        decimal amount
        string type "CREDIT | DEBIT"
        string reason "REFERRAL_REWARD | BOOKING_DISCOUNT"
        datetime timestamp
    }

    PROMOTION {
        string code PK
        string type "PERCENTAGE | FLAT"
        decimal value
        datetime expires_at
        int usage_limit_per_user
        boolean is_active
    }

    BOOKING ||--o| PROMOTION : "utilise"
    WALLET ||--o{ WAL_TRANS : "historise"
    BOOKING ||--o{ WAL_TRANS : "génère/consomme"
```

### 2. Diagramme de Flux (BPMN)

Ce diagramme détaille le cycle de vie du parrainage, de l'invitation à la récompense.

```mermaid
graph TD
    A[Validation Compte Utilisateur] --> B[Génération Code Parrainage Unique]
    B --> C[Partage du code par le Parrain]
    C --> D[Inscription du Filleul avec le Code]
    D --> E{Première Réservation?}
    E -- Oui --> F[Application Réduction Immédiate]
    E -- Non --> G[Tarif Standard]
    F --> H[Réalisation de la Prestation]
    H --> I{Paiement validé & Sans Litige?}
    I -- Oui --> J[Crédit du Wallet du Parrain]
    I -- Non --> K[Récompense en attente/annulée]
    J --> L[Utilisation du crédit sur prochaine transaction]
```

### 3. Critères d'Acceptation (Given/When/Then)

#### Scénario 1 : Génération automatique du code
**Given** Un nouvel utilisateur (Homer ou Cleaner) vient de faire valider son compte par le système.
**When** Le compte passe au statut "VALIDATED".
**Then** Un code de parrainage unique et permanent est généré et associé à son profil.

#### Scénario 2 : Utilisation d'un code de parrainage par un filleul
**Given** Un nouveau Homer souhaite effectuer sa première réservation.
**And** Il possède le code de parrainage de son parrain.
**When** Il saisit le code dans le champ "Code Promo/Parrainage" avant le paiement.
**Then** Le montant total à payer est réduit de la valeur définie (ex: 15%).
**And** La transaction est marquée comme liée à ce parrainage.

#### Scénario 3 : Récompense du parrain après service fait
**Given** Un filleul a effectué sa première prestation avec un code de parrainage.
**When** Le statut de la réservation passe à "COMPLETED" et le paiement est confirmé sans litige après le délai légal.
**Then** Le portefeuille virtuel (Wallet) du parrain est crédité du montant de la récompense.
**And** Le parrain reçoit une notification de succès.

#### Scénario 4 : Utilisation du crédit cumulé (Homer)
**Given** Un Homer possède 20€ de crédit dans son Wallet Sweet-Home.
**And** Il réserve une prestation de 50€.
**When** Il arrive à l'étape du paiement.
**Then** Le système déduit automatiquement les 20€ du Wallet du total.
**And** Le montant final débité sur sa carte bancaire est de 30€.

#### Scénario 5 : Code promotionnel administratif
**Given** Un code promo "WELCOME2026" créé par l'admin avec une limite de 1 utilisation par utilisateur.
**When** Un utilisateur tente d'utiliser ce code pour la deuxième fois.
**Then** Le système rejette le code avec le message "Code déjà utilisé ou expiré".