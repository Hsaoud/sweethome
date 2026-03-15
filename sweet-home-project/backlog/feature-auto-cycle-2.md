- Titre : Moteur de recherche et filtrage des Cleaners
- Contexte : Suite à la mise en place de l'authentification et des profils spécifiques (Homer/Cleaner) lors du Cycle 1, la suite logique pour une marketplace est de permettre la mise en relation. Le moteur de recherche est l'outil indispensable pour que les Homers puissent découvrir les prestataires disponibles dans leur zone.
- User Story : En tant que Homer, je veux rechercher des Cleaners par ville et filtrer par catégorie de service afin de trouver le prestataire idéal pour mon besoin de ménage.
- Règles Métier :
1. Recherche par ville : Le système doit filtrer les résultats en fonction de la ville saisie par l'utilisateur (champ `city` de la table `cleaners`).
2. Filtrage par Catégorie : Possibilité d'affiner la recherche en sélectionnant une ou plusieurs catégories de services (via la table `categories`).
3. Statut de disponibilité : Seuls les profils de Cleaners ayant le flag `available = true` doivent apparaître dans les résultats de recherche.
4. Tri des résultats : Proposer par défaut un tri par tarif horaire (`hourly_rate`) croissant, avec une option de tri par expérience (`experience_years`).
5. Affichage des informations : Chaque carte de résultat doit afficher le prénom, la photo de profil, la ville, le tarif horaire et la note moyenne calculée (à partir de la table `reviews`).