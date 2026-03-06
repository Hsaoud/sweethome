# Spécification : Planification Intelligente (Smart Scheduling)

> **Important** : Cette trajectoire applicative est envisagée pour la Phase Post-MVP.

## Description
Les prestataires enchaînent souvent des interventions chez de nombreux clients le même jour. Sans vision périphérique rigoureuse, ils risquent d'accepter des RDVs impossibles à honorer logistiquement (la distance de trajet étant trop longue). Le système l'aidera à éviter cela.

## Fonctionnalités (Alerte Trajet Réseau)
1. **Calcul de distance / temps de trajet** :
   - Connexion logicielle avec un système de guidage (API Mapbox, OSRM, Google Maps Distance Matrix) afin de calculer pragmatiquement le temps physique séparant l'adresse A (Prestation terminée à X heures) et l'adresse B (Nouvelle prestation à P heures).
2. **Warning Contextuel (Au moment d'accepter)** :
   - Lorsque le Cleaner tente d'accepter la demande de réservation d'un Homer (ou de postuler à une de ses annonces).
   - Le système contrôle l'intervention précédente et/ou suivante immédiate.
   - Constat : S'il y a un recouvrement de temps, ou un intervalle impossible.
   - *Message affiché à l'écran* : "⚠️ Attention, votre précédent RDV se situe à 15 min de route d'ici. Vous avez très peu de marge et risquez d'arriver en retard. Êtes-vous sûr d'accepter ce créneau ?"
