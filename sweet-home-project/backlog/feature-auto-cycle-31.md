- Titre : [Optimisation de Tournée et Calcul d'Itinéraire pour les Cleaners]
- Contexte : Avec la maturité de la plateforme et l'augmentation du volume de réservations, les prestataires (Cleaners) enchaînent souvent plusieurs missions par jour. L'optimisation de leurs déplacements est devenue un levier majeur de productivité et de ponctualité. Cette feature fait suite à la gestion du calendrier (Cycle 9) et au suivi en temps réel (Cycle 25) en y ajoutant une couche d'intelligence logistique.
- User Story : En tant que Cleaner, je veux que l'application calcule et affiche l'itinéraire optimal entre mes différentes missions de la journée afin de minimiser mes temps de trajet et d'informer automatiquement les clients de mon heure d'arrivée estimée.
- Règles Métier :
1. Calcul automatique de l'itinéraire le plus court/rapide entre les adresses des missions confirmées pour une même journée.
2. Intégration d'une API cartographique (type Google Maps ou Mapbox) pour fournir le temps de trajet en tenant compte du trafic en temps réel.
3. Affichage d'une vue "Ma Journée" sous forme de timeline cartographiée avec les étapes numérotées.
4. Envoi d'une notification prédictive au "Homer" suivant en cas de retard détecté sur le trajet entre deux missions.
5. Bouton "Lancer le guidage" permettant d'ouvrir l'application de navigation favorite du smartphone (Waze, Google Maps, Apple Maps) avec les coordonnées de la mission.