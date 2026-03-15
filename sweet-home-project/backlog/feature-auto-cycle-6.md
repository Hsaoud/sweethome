# Titre : [Système de Paiement Sécurisé et Séquestre des Fonds]

# Contexte
Maintenant que le cycle de mise en relation est complet (Recherche, Réservation, Messagerie) et que la confiance est établie par le système de notation, l'étape suivante pour transformer Sweet-Home en une véritable place de marché transactionnelle est l'automatisation des flux financiers. Cela permet de garantir le paiement pour le Cleaner et de sécuriser les fonds de l'Homer jusqu'à la réalisation effective de la prestation.

# User Story
En tant qu'Homer, je veux payer ma prestation directement en ligne au moment de la réservation afin que les fonds soient bloqués en séquestre et reversés automatiquement au Cleaner une fois le ménage effectué et validé.

# Règles Métier
1. **Gestion des Moyens de Paiement** : L'Homer doit pouvoir enregistrer et utiliser une carte bancaire pour régler sa réservation.
2. **Système de Séquestre** : Les fonds sont débités lors de la confirmation de la réservation par le Cleaner, mais conservés par la plateforme (tiers de confiance).
3. **Portefeuille Cleaner (Payout)** : Le Cleaner doit renseigner ses coordonnées bancaires (IBAN/BIC) pour permettre le virement de ses gains.
4. **Déblocage des Fonds** : Le virement vers le compte du Cleaner est déclenché automatiquement 24h après la date de fin de la prestation, sauf signalement d'un litige.
5. **Statuts de Paiement** : Mise à jour de la table `bookings` pour suivre le cycle de vie financier : `AWAITING_PAYMENT`, `PAID_ESCROW` (en séquestre), `RELEASED` (versé), `REFUNDED` (remboursé).
6. **Facturation Automatique** : Génération d'un document PDF (facture ou reçu) après chaque transaction, téléchargeable par l'Homer et le Cleaner depuis leur tableau de bord.