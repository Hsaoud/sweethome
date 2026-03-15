- Titre : [Système de Suivi de l'Arrivée du Prestataire en Temps Réel (Live Tracking)]
- Contexte : Après avoir sécurisé les accès (Cycle 18) et automatisé le check-in/out (Cycle 15), la suite logique pour parfaire l'expérience utilisateur "on-demand" est de permettre au Homer de suivre l'approche du prestataire. Cela réduit l'incertitude liée au trajet et permet de préparer l'accueil ou le déclenchement des protocoles d'entrée au moment exact de l'arrivée.
- User Story : En tant qu'Homer, je veux visualiser la position géographique du cleaner sur une carte 30 minutes avant le début de sa prestation afin de m'organiser et de recevoir une notification automatique lorsqu'il entre dans mon périmètre immédiat.
- Règles Métier :
1. Le partage de position GPS s'active automatiquement pour le prestataire uniquement 30 minutes avant l'heure prévue de la réservation.
2. Une carte interactive (Real-time Tracking) devient visible sur le tableau de bord de l'Homer dès l'activation du partage.
3. Notification "Arrivée Imminente" envoyée à l'Homer via un système de géofencing lorsque le prestataire entre dans un rayon de 500 mètres du domicile.
4. Le partage de position se coupe obligatoirement et immédiatement dès que le prestataire valide son "Check-in" (début de mission).
5. Respect de la vie privée : Le prestataire peut désactiver le tracking manuel, mais un indicateur de "Transparence" sera impacté sur son profil public.