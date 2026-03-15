- Titre : Moteur de Recherche et Filtrage des Cleaners
- Contexte : Suite à la complétion des profils et à la spécialisation des utilisateurs (Cycle 1), la base de données dispose désormais des tarifs, des compétences et de la localisation des prestataires. L'étape suivante cruciale pour transformer l'application en marketplace est de permettre aux clients (Homers) de trouver et filtrer les prestataires (Cleaners) adaptés à leurs besoins.
- User Story : En tant que Homer, je veux rechercher des Cleaners par ville et filtrer les résultats par catégorie de service et tarif afin de trouver le prestataire idéal disponible près de chez moi.
- Règles Métier :
1. **Filtre de Ville** : La recherche doit filtrer les Cleaners en fonction de la ville saisie (champ `city` de la table `cleaners`).
2. **Filtrage par Catégories** : Possibilité d'affiner la recherche en sélectionnant une ou plusieurs catégories de services (via la table de jointure `cleaner_categories`).
3. **Condition de Disponibilité** : Seuls les profils de Cleaners ayant le statut `available = true` doivent apparaître dans les résultats.
4. **Tri des Résultats** : Par défaut, les résultats sont triés par tarif horaire (`hourly_rate`) croissant, avec une option pour trier par expérience (`experience_years`).
5. **Affichage des Informations** : Chaque carte de résultat doit obligatoirement afficher le prénom, la photo de profil, la ville, le tarif horaire, et la note moyenne calculée (basée sur la table `reviews`).
6. **Gestion des Cas Vides** : Afficher un message explicite "Aucun Cleaner disponible pour cette recherche" si aucun résultat ne correspond aux critères.