- Titre : [Vérification d'Identité (KYC) et Certification des Profils]
- Contexte : Avec un système de paiement et de notation déjà en place, la vérification d'identité est l'étape ultime pour garantir la sécurité des échanges physiques et transformer Sweet-Home en une plateforme de confiance. C'est le levier indispensable pour rassurer les Homers avant de laisser un inconnu entrer chez eux.
- User Story : En tant qu'utilisateur (Homer ou Cleaner), je veux soumettre mes documents d'identité afin d'obtenir un badge "Profil Vérifié" et renforcer ma crédibilité sur la plateforme.
- Règles Métier : 
    1. Ajout d'un champ `verification_status` (ENUM : `NOT_STARTED`, `PENDING`, `VERIFIED`, `REJECTED`) dans la table `users`.
    2. Interface dédiée dans le profil pour l'upload de documents (Pièce d'identité recto/verso).
    3. Affichage d'un badge visuel "Identité Vérifiée" sur le profil public et dans les résultats du moteur de recherche.
    4. Les Cleaners ayant le statut `VERIFIED` bénéficient d'un boost algorithmique dans le classement des recherches.
    5. Restriction : Le déblocage des fonds vers le compte bancaire du Cleaner (issu du séquestre) est conditionné par le statut `VERIFIED`.
    6. Envoi d'une notification (Email/Messagerie) dès que le statut de vérification est mis à jour par le système.