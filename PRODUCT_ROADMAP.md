# Sweet Home - Plan Fonctionnel (Product Roadmap)

Ce document centralise la vision produit et les différentes étapes de développement pour amener la plateforme "Sweet Home" vers une version prête pour la production.

## État Actuel (Fondations)
- [x] Architecture globale (Frontend Angular, Backend Spring Boot, BDD PostgreSQL).
- [x] CI/CD locale & Environnements de test (JUnit, Playwright, TDD imposé).
- [x] Système d'authentification de base (Inscription / Connexion pour Homers et Cleaners).

---

## Phase 1 : Le Coeur du Service (MVP - Minimum Viable Product)
*Objectif : Permettre à un Homer de trouver un Cleaner et de réserver une prestation.*

**Fonctionnalités proposées :**
1. **Profils et Compétences** : Les Cleaners peuvent détailler leurs tarifs, leurs compétences spécifiques (repassage, vitres, nettoyage profond) et leur zone d'intervention.
2. **Recherche & Filtrage** : Le Homer peut rechercher un Cleaner selon sa ville, ses dates, et le trier par prix ou note.
3. **Système de Réservation (Booking)** : 
   - Le Homer fait une demande de réservation avec des dates/heures précises.
   - Le Cleaner peut Accepter ou Refuser la demande.
4. **Tableaux de bord (Dashboards)** :
   - Homer : Historique des prestations, réservations en attente.
   - Cleaner : Planning des interventions, revenus générés, demandes en attente.

---

## Phase 2 : Confiance et Rétention (Production Locale)
*Objectif : Sécuriser la plateforme et instaurer la confiance entre les utilisateurs.*

**Fonctionnalités proposées :**
1. **Avis et Notations** : Après une prestation, le Homer peut noter (1 à 5 étoiles) et laisser un avis publiquement sur le profil du Cleaner.
2. **Messagerie Interne** : Permettre au Homer et au Cleaner de discuter des détails (ex: code d'entrée, matériel à fournir) une fois la réservation acceptée, sans donner leurs numéros personnels immédiatement.
3. **Paiement (Optionnel mais recommandé)** : Intégration de Stripe pour bloquer les fonds lors de la réservation, et payer le Cleaner (moins la commission de la plateforme) une fois la prestation terminée.

---

## Phase 3 : Scale & Opérationnel (Mise à l'échelle)
*Objectif : Gérer le volume et les litiges de manière professionnelle.*

**Fonctionnalités proposées :**
1. **Panel Administrateur (Back-office)** : Pour gérer les utilisateurs bloqués, les litiges, et surveiller les métriques de la plateforme.
2. **Vérification d'Identité (KYC)** : Vérification des cartes d'identité des Cleaners pour des raisons de sécurité juridique.
3. **Réservations Récurrentes** : Permettre à un Homer de réserver un Cleaner de manière automatisée (ex: tous les vendredis à 14h).

---

> **Note :** Ce document est vivant. Il sera mis à jour au fur et à mesure que les règles de gestion (Business Rules) seront définies.
