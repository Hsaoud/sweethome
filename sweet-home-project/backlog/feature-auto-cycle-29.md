- Titre : [Système de Gifting et Cartes Cadeaux Sweet-Home]

- Contexte : Avec une plateforme désormais mature techniquement et fonctionnellement (réservation, paiement, KYC, récurrence), l'enjeu se déplace vers l'acquisition de nouveaux utilisateurs. Le système de cartes cadeaux permet de transformer nos utilisateurs actuels en prescripteurs en leur permettant d'offrir des heures de ménage pour des occasions spéciales (naissance, déménagement, fêtes), créant ainsi un canal de croissance organique puissant.

- User Story : En tant qu'utilisateur (Acheteur), je veux acheter et envoyer une carte cadeau créditée afin de faire découvrir le service à un proche. En tant que destinataire (Bénéficiaire), je veux activer ce crédit sur mon compte pour payer mes prochaines prestations de ménage.

- Règles Métier :
1. **Achat Flexible** : Proposer des montants prédéfinis (50€, 100€, 150€) ou la saisie d'un montant libre (minimum 30€).
2. **Génération de Code** : Chaque achat génère un code unique alphanumérique sécurisé et à usage unique (lié à un montant).
3. **Personnalisation et Envoi** : L'acheteur peut saisir l'email du bénéficiaire, un message personnalisé et choisir une date d'envoi programmée.
4. **Activation du Crédit** : Le bénéficiaire doit posséder ou créer un compte "Homer" pour activer le code. Une fois activé, le montant est transféré sur un solde créditeur interne.
5. **Utilisation Fractionnable** : Le solde cadeau peut être utilisé pour payer tout ou partie d'une ou plusieurs réservations jusqu'à épuisement du montant.
6. **Priorité de Débit** : Lors d'une réservation, le système doit proposer par défaut d'utiliser le solde cadeau avant de débiter la carte bancaire enregistrée.
7. **Validité Temporelle** : La carte cadeau est valide pendant 12 mois à partir de sa date d'achat.
8. **Gestion Comptable** : Une facture d'achat de "crédit" doit être générée pour l'acheteur, et la prestation finale payée par carte cadeau doit être traitée comme une transaction classique pour le Cleaner (règlement total).