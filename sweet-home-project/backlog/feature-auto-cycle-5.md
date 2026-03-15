- Titre : [Système de Notation et d'Évaluation des Prestations]
- Contexte : Après la réservation et l'échange via la messagerie interne, la prestation de ménage a lieu. Pour garantir la qualité de service et la confiance au sein de la communauté (aspect "Blablacar"), il est désormais crucial de permettre aux utilisateurs de s'évaluer mutuellement. C'est l'étape finale du cycle de service qui alimente le moteur de recherche via le score de réputation.
- User Story : En tant qu'utilisateur (Homer ou Cleaner), je veux laisser une note et un commentaire après une prestation terminée afin de partager mon expérience et d'aider les autres membres à identifier les profils les plus fiables.
- Règles Métier : 
    1. Un avis ne peut être déposé que si la réservation (Booking) possède le statut "TERMINÉE" (COMPLETED).
    2. L'évaluation se compose d'une note obligatoire (de 1 à 5 étoiles) et d'un commentaire facultatif (limité à 1000 caractères).
    3. Le système doit être bidirectionnel : le Homer évalue la qualité du ménage, et le Cleaner évalue la fiabilité du Homer.
    4. Un utilisateur ne peut pas s'auto-évaluer.
    5. La note moyenne d'un Cleaner doit être automatiquement recalculée et mise à jour sur son profil public et dans les résultats de recherche.
    6. Les avis sont définitifs et ne peuvent être modifiés une fois publiés pour éviter les pressions après coup.