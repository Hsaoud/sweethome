- Titre : [Génération Automatisée de Factures et Attestations Fiscales]
- Contexte : Après avoir sécurisé les paiements (Cycle 6) et mis en place les prestations récurrentes (Cycle 8), la prochaine étape cruciale pour professionnaliser la plateforme est la gestion administrative. En France notamment, le crédit d'impôt pour les services à la personne nécessite des factures conformes et une attestation fiscale annuelle, ce qui constitue un argument de vente majeur pour les Homers et une obligation légale pour les Cleaners.
- User Story : En tant qu'utilisateur (Homer ou Cleaner), je veux pouvoir télécharger mes factures après chaque prestation et obtenir un récapitulatif annuel de mes transactions afin de simplifier ma comptabilité et mes déclarations fiscales.
- Règles Métier : 
    1. Une facture au format PDF doit être générée automatiquement dès qu'un paiement est débloqué du séquestre vers le Cleaner.
    2. La facture doit obligatoirement comporter : les coordonnées des deux parties, la date et la durée de la prestation, le montant brut, la commission de la plateforme, et le montant net perçu.
    3. Les Homers doivent disposer d'un bouton "Télécharger mon attestation fiscale" regroupant toutes les prestations éligibles de l'année civile précédente.
    4. Un tableau de bord financier doit être ajouté au profil du Cleaner pour visualiser l'évolution de ses revenus (mensuel/annuel).
    5. Les factures doivent être stockées de manière sécurisée et rester accessibles même si le profil de l'autre partie est désactivé.