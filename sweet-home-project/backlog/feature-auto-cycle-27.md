- Titre : [Gestion des Réservations Multi-Prestataires (Équipes)]
- Contexte : Avec l'implémentation de la gestion multi-logements et des prestations pour grandes surfaces, une seule personne ne suffit plus pour certaines missions (ex: emménagement/déménagement, nettoyage de printemps). Permettre la réservation d'une équipe est la suite logique pour adresser des besoins plus complexes et augmenter le panier moyen.
- User Story : En tant que Homer, je veux pouvoir réserver plusieurs cleaners simultanément pour une même prestation afin de réduire la durée d'intervention pour les grandes surfaces ou les besoins intensifs.
- Règles Métier :
    1. Évolution du modèle de données pour permettre l'association de plusieurs `cleaner_id` à un seul `booking_id` (via une table de jointure `booking_assignments`).
    2. Désignation d'un "Cleaner Référent" (Lead) par mission pour centraliser la communication et la validation de la checklist.
    3. Calcul dynamique du prix total basé sur la somme des tarifs horaires de chaque intervenant multipliée par la durée de la prestation.
    4. Vérification de la disponibilité croisée : la réservation n'est possible que si tous les cleaners sélectionnés sont libres sur le créneau choisi.
    5. Gestion des notifications : chaque membre de l'équipe reçoit sa propre alerte et doit valider individuellement sa participation.