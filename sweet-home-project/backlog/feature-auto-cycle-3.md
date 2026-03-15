- Titre : Moteur de recherche et filtrage des Cleaners
- Contexte : Maintenant que les profils de prestataires (Cleaners) sont configurés avec leurs tarifs, compétences et zones d'intervention dans la base de données, la suite logique est de permettre aux clients (Homers) de les trouver via un outil de recherche performant pour initier le tunnel de réservation.
- User Story : En tant que Homer, je veux rechercher des Cleaners par ville, catégorie et tarif afin de trouver le prestataire idéal disponible près de chez moi.
- Règles Métier :
    1. La recherche doit permettre un filtrage par ville (basé sur `cleaners.city`).
    2. Possibilité de filtrer par catégorie de service (via la table `categories`).
    3. Les résultats ne doivent afficher que les Cleaners ayant le statut `available = true`.
    4. Les profils doivent être triés par défaut par leur note moyenne (calculée via la table `reviews`).
    5. Le moteur doit vérifier que la position du Homer (latitude/longitude) est incluse dans le `action_radius_km` du Cleaner.
    6. Chaque résultat de recherche doit afficher : Prénom, Photo, Note moyenne, Tarif horaire et Headline.