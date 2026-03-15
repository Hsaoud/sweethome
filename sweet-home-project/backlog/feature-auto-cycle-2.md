# Titre : [Réservation d'une Prestation de Ménage]

# Contexte :
Suite à l'implémentation du moteur de recherche et du filtrage, l'Homer est désormais capable d'identifier les Cleaners correspondant à ses besoins. La suite logique est de permettre la mise en relation transactionnelle via un système de réservation. C'est l'étape indispensable pour transformer l'intention de recherche en une prestation concrète et alimenter le cycle de vie des données (bookings).

# User Story :
En tant qu'**Homer**, je veux pouvoir **envoyer une demande de réservation** à un Cleaner depuis son profil afin de **planifier une intervention de ménage** à une date et heure précises.

# Règles Métier :
- **Authentification obligatoire** : L'utilisateur doit être connecté avec le rôle 'HOMER' pour soumettre une réservation.
- **Accès au service** : Le formulaire de réservation est accessible via la page de détail d'un Cleaner.
- **Saisie des données** : L'Homer doit obligatoirement renseigner la date, l'heure de début, l'heure de fin et la surface (m²) de l'intervention.
- **Cohérence temporelle** : La date de réservation ne peut pas être dans le passé et l'heure de fin doit être strictement supérieure à l'heure de début.
- **Calcul du prix** : Le système doit calculer et afficher le montant total prévisionnel (`total_price`) avant la validation (basé sur le `hourly_rate` du Cleaner et la durée de la prestation).
- **Persistance** : À la validation, une entrée est créée dans la table `bookings` avec le statut initial `PENDING`.
- **Validation d'adresse** : Un Homer ne peut pas valider de réservation s'il n'a pas complété son adresse (rue, ville, code postal) dans son profil.