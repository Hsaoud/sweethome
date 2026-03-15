- Titre : [Système de Check-in/Check-out et Preuve de Prestation par Photo]
- Contexte : Après avoir implémenté le centre de résolution des litiges (Cycle 10) et le paiement sécurisé (Cycle 6), il est désormais crucial de fournir des preuves tangibles de la réalisation de la prestation. Cette fonctionnalité permet de sécuriser le paiement pour le Cleaner et d'apporter une garantie de service au Homer, tout en fournissant des éléments factuels au support en cas de contestation.
- User Story : En tant que Cleaner, je veux valider mon arrivée et mon départ sur l'application et soumettre des photos de fin de mission afin de justifier mon travail, déclencher le paiement et protéger ma réputation.
- Règles Métier : 
    1. Le bouton "Démarrer la prestation" (Check-in) n'est actif que si le Cleaner est géolocalisé dans un rayon de 200m de l'adresse du Homer.
    2. L'horodatage du Check-in et du Check-out est enregistré de manière immuable en base de données.
    3. Pour valider le "Check-out", le Cleaner doit obligatoirement uploader au minimum 3 photos des zones traitées.
    4. La validation du Check-out fait passer automatiquement le statut de la réservation de `IN_PROGRESS` à `COMPLETED`.
    5. Le Homer reçoit une notification push/email immédiate lors du Check-out contenant le lien vers les photos de la prestation.
    6. En cas de litige ouvert via le Centre de Résolution, ces photos sont les pièces justificatives prioritaires pour l'arbitrage.