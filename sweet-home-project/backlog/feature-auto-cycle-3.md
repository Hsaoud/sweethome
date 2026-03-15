### [Système d'Avis et de Notation]

**Contexte :**
Avec un système de recherche et de réservation désormais opérationnel, la confiance entre les membres devient le pilier central de la plateforme. L'implémentation du système d'avis permet de boucler le cycle de vie d'une prestation en offrant une preuve sociale indispensable. Elle valorise les meilleurs prestataires (Cleaners) et sécurise les clients (Homers), conformément au modèle "Blablacar" où la réputation est la clé de l'usage.

**User Story :**
En tant qu'utilisateur (Homer ou Cleaner), je veux pouvoir laisser une note et un commentaire à l'autre partie une fois la prestation terminée, afin de contribuer à la fiabilité de la communauté et d'aider les autres membres à choisir leurs prestataires.

**Règles Métier :**
1. **Éligibilité :** Un avis ne peut être déposé que pour une réservation (`Booking`) dont le statut est strictement égal à 'COMPLETED'.
2. **Unicité :** Chaque réservation ne peut donner lieu qu'à un seul avis par partie (le Homer note le Cleaner, et inversement).
3. **Notation :** La note (`rating`) est un entier obligatoire compris entre 1 (très déçu) et 5 (excellent).
4. **Commentaire :** Le texte de l'avis est obligatoire, avec une longueur minimale de 20 caractères et maximale de 1000 caractères.
5. **Intégrité :** Un utilisateur ne peut pas déposer un avis sur son propre profil (le `reviewer_id` doit être différent du `reviewee_id`).
6. **Visibilité :** Les avis reçus doivent être affichés chronologiquement sur le profil public de l'utilisateur concerné.
7. **Calcul de Réputation :** La note moyenne de l'utilisateur doit être recalculée et mise à jour sur son profil (et dans les résultats de recherche pour les Cleaners) à chaque nouvelle publication d'avis.