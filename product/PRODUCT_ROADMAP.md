# Sweet Home - Plan Fonctionnel (Product Roadmap)

Ce document centralise la vision produit à haut niveau pour la plateforme "Sweet Home". Les détails techniques et métiers de chaque fonctionnalité sont documentés dans le dossier `product/specs/`.

## Phase 1 : Le Cœur du Service (MVP - Minimum Viable Product)
*Objectif : Permettre à un Homer de trouver un Cleaner, visualiser ses disponibilités complexes, et finaliser la réservation avec une tarification dynamique.*

1. **Recherche & Match** : Le Homer recherche le Cleaner.
   - 📄 *Détails : `product/specs/01-booking-flow.md`*
2. **Gestion Avancée du Calendrier (Cœur de l'application)** : Gestion de la disponibilité avec récurrence, exceptions et granularité à la demi-heure. 
   - 📄 *Détails : `product/specs/02-calendar-management.md`*
3. **Tarification Dynamique** : Les Cleaners paramètrent leurs tarifs (selon m², prestations).
   - 📄 *Détails : `product/specs/03-pricing.md`*

---

## Phase 2 : Confiance et Rétention (Production Locale)
*Objectif : Sécuriser la plateforme et rassurer les utilisateurs.*

1. **Avis et Notations** : Évaluation post-prestation pour la réputation, visible publiquement.
2. **Messagerie Interne** : Système de chat sécurisé une fois le RDV validé.
3. **Paiement Intégré** : Séquestre des fonds (via Stripe) pour prélèvement et versement garantis avec commission plateforme.

---

## Phase 3 : Évolutions Futures & Scale (Post MVP)
*Objectif : Attirer les nouveaux Cleaners, optimiser les trajets et gérer la communauté à grande échelle.*

1. **Annonces Homers ("Offres d'emploi")** : Le Homer publie un besoin avec un prix fixé pour attirer les Cleaners débutants.
   - 📄 *Détails : `product/specs/04-homer-ads.md`*
2. **Smart Scheduling (Temps de Trajet)** : Avertissement intelligent pour le Cleaner si deux RDV consécutifs sont trop éloignés géographiquement.
   - 📄 *Détails : `product/specs/05-smart-scheduling.md`*
3. **KYC et Panel Administrateur** : Vérification des identités des travailleurs et gestion globale (litiges, modérations).
