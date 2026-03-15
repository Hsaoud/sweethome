- Titre : Complétion du Profil et Spécialisation (Onboarding Homer & Cleaner)
- Contexte : L'authentification de base est opérationnelle, mais les utilisateurs ne sont pour l'instant que des entités génériques. Cette étape est cruciale pour structurer les données spécifiques aux deux rôles du projet (Homer et Cleaner), permettant ainsi d'alimenter le futur moteur de recherche et le système de réservation.
- User Story : En tant qu'utilisateur nouvellement inscrit, je veux compléter mon profil en choisissant mon rôle (Homer ou Cleaner) et en saisissant mes informations spécifiques afin de pouvoir soit proposer mes services de ménage, soit rechercher un prestataire pour mon domicile.
- Règles Métier : 
    1. **Choix du Rôle** : L'utilisateur doit explicitement choisir entre le profil `HOMER` (client) ou `CLEANER` (prestataire).
    2. **Données Homer** : Si le rôle est `HOMER`, la saisie de l'adresse complète (rue, ville, code postal) est obligatoire pour permettre la géolocalisation des besoins.
    3. **Données Cleaner** : Si le rôle est `CLEANER`, l'utilisateur doit obligatoirement renseigner son tarif horaire (`hourly_rate`), sa biographie (`bio`), son périmètre d'intervention (`action_radius_km`) et sa ville.
    4. **Spécialisation Cleaner** : Un `CLEANER` doit sélectionner au moins une catégorie de service (ex: Ménage classique, Repassage, Vitres) parmi la table `categories`.
    5. **Géocodage** : Le système doit automatiquement convertir l'adresse saisie en coordonnées `latitude` et `longitude` dans la table `users`.
    6. **Validation** : Le tarif horaire doit être supérieur à zéro et la biographie doit contenir un minimum de 100 caractères pour assurer la qualité des profils.