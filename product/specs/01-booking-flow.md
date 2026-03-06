# Spécification : Flux de Réservation (Booking Flow)

## Description
Le processus central du MVP consiste à permettre au client ("Homer") de trouver un prestataire ("Cleaner") et d'effectuer une demande de réservation. Contrairement à un simple service de mise en relation, c'est le Homer qui est à l'initiative de la recherche dans ce flux.

## Fonctionnement
1. **Recherche par le Homer** :
   - Le Homer accède à un moteur de recherche.
   - Filtres de base : Localisation (ville/distance), Date/Heure souhaitée.
   - L'algorithme retourne la liste des Cleaners correspondants (disponibles et officiant dans la zone).
2. **Demande de Prestation** :
   - Le Homer consulte le profil d'un Cleaner.
   - Il sélectionne un créneau disponible (voir `02-calendar-management.md`).
   - Il valide sa demande (le paiement sera ajouté en Phase 2).
3. **Validation par le Cleaner** :
   - Le Cleaner reçoit une notification de demande.
   - Il a la possibilité d'accepter ou de refuser cette demande.
   - **Important** : Dès l'acceptation, le créneau devient automatiquement "occupé" dans l'agenda du Cleaner, empêchant tout conflit (double booking).
