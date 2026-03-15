- Titre : [Gestion du Calendrier de Disponibilités et des Indisponibilités]
- Contexte : Suite à l'implémentation des réservations récurrentes (Cycle 8), il est devenu critique de permettre aux Cleaners de piloter leur emploi du temps. Sans calendrier dynamique, le risque de conflits de réservation (overbooking) est élevé. Cette feature permet d'automatiser la vérification des disponibilités avant chaque demande de réservation.
- User Story : En tant que Cleaner, je veux définir mes plages horaires de travail et mes périodes d'absence afin que les Homers ne puissent réserver que sur mes créneaux réellement disponibles.
- Règles Métier :
    1. Le Cleaner doit avoir accès à une interface de calendrier (vue hebdomadaire/mensuelle) sur son tableau de bord.
    2. Le Cleaner peut définir des "Disponibilités Récurrentes" (ex: tous les mardis de 09h00 à 17h00).
    3. Le Cleaner peut déclarer des "Indisponibilités Ponctuelles" (congés, rendez-vous) qui priment sur les disponibilités récurrentes.
    4. Toute réservation (Booking) dont le statut est "Confirmé" doit automatiquement bloquer le créneau correspondant dans le calendrier.
    5. Lors du processus de réservation par un Homer, le système doit bloquer la sélection de créneaux qui chevauchent une indisponibilité ou une réservation existante.
    6. Un Cleaner sans aucune disponibilité définie ne doit plus apparaître dans les résultats du moteur de recherche par défaut.