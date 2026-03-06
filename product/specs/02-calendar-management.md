# Spécification : Gestion du Calendrier (Calendar Management)

## Description
Il s'agit du composant technique le plus complexe et le plus critique de l'application. Son objectif est de donner le plein contrôle au Cleaner sur ses disponibilités, s'inspirant fortement de la gestion des logiciels comme Outlook ou Google Calendar.

## Fonctionnalités Clés
1. **Créneaux Récurrents** : 
   - Le Cleaner peut définir des schémas répétitifs (ex: "Disponible tous les week-ends de 09:00 à 18:00").
2. **Exceptions (Suppression d'occurrence)** : 
   - La possibilité d'annuler une instance précise sans détruire la récurrence. (ex: "Sauf ce samedi 14 juin car je suis en congés").
3. **Créneaux Ponctuels (Manuels)** : 
   - Ajouter manuellement des disponibilités spécifiques pour une date unique.
4. **Granularité à la Demi-Heure** :
   - Le calendrier doit supporter un découpage fin (tranches de 30 minutes) pour optimiser les plannings du Cleaner.
5. **Changement d'état automatique** : 
   - Lorsqu'une demande de réservation effectuée par un Homer est **acceptée**, les créneaux impactés perdent instantanément leur statut "Disponible" pour passer en "Occupé", barrant ainsi la route au double booking.
