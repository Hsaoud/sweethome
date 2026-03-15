Je vais analyser l'historique et l'état actuel de la base de données pour identifier la suite logique du produit.

L'historique montre un produit déjà très mature avec la réservation, le paiement, le KYC, les abonnements et même la gestion des litiges. Pour compléter l'expérience utilisateur et assurer la rétention, il manque une brique essentielle de communication proactive : un système de notifications centralisé.

Voici le ticket pour la prochaine fonctionnalité :

# Titre : [Système de Notifications Centralisé et Alertes Multi-Canal]

### Contexte
Maintenant que le cycle de vie complet d'une prestation est implémenté (de la recherche au litige, en passant par le paiement et l'abonnement), il est crucial de garantir que les utilisateurs ne manquent aucune étape clé. Actuellement, l'utilisateur doit se connecter pour vérifier ses messages ou l'état de ses réservations. Un système de notifications est la suite logique pour augmenter l'engagement, réduire les délais de réponse (notamment pour les réservations et les litiges) et assurer le bon déroulement des prestations récurrentes.

### User Story
En tant qu'**utilisateur (Homer ou Cleaner)**, je veux **recevoir des notifications en temps réel (In-App et Email)** pour **être informé immédiatement des événements importants liés à mon compte et à mes prestations** afin de **réagir rapidement et ne manquer aucune opportunité ou échéance.**

### Règles Métier
1. **Types de Notifications** :
    - **Booking** : Nouvelle demande, confirmation, annulation, rappel 24h avant.
    - **Messaging** : Nouveau message reçu.
    - **Payment** : Confirmation de paiement, échec de prélèvement (pour les abonnements).
    - **KYC** : Validation ou refus des documents d'identité.
    - **Dispute** : Nouvelle étape dans un litige ouvert.
2. **Canaux** :
    - **In-App** : Badge sur une icône "cloche" dans la navbar et liste des notifications non lues.
    - **Email** : Envoi automatique pour les événements critiques (réservations, paiements, messages).
3. **Préférences** : L'utilisateur doit pouvoir activer/désactiver chaque type de notification par canal depuis ses réglages.
4. **État** : Chaque notification doit avoir un état (Lue / Non lue).
5. **Persistance** : Les notifications doivent être stockées en base de données pour consultation ultérieure.