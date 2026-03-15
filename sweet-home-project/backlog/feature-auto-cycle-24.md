- Titre : [Système de Synchronisation Calendrier Externe (Google/iCal)]
- Contexte : Suite à l'implémentation de la gestion des disponibilités (Cycle 9) et de la réservation instantanée (Cycle 23), il est devenu critique de permettre aux prestataires de synchroniser leur activité Sweet-Home avec leurs outils personnels (Google Calendar, Outlook, Apple Calendar). Cela garantit la fiabilité des créneaux et évite les conflits d'emploi du temps ou les doubles réservations.
- User Story : En tant que Cleaner, je veux lier mon calendrier personnel à mon compte Sweet-Home afin de centraliser mes rendez-vous et de bloquer automatiquement mes disponibilités sur la plateforme sans action manuelle.
- Règles Métier :
1. Génération d'un flux iCal sortant unique et sécurisé par utilisateur pour exporter les réservations Sweet-Home vers des agendas tiers.
2. Possibilité pour le Cleaner d'ajouter une URL de flux iCal externe (ex: Google Calendar) pour importer ses indisponibilités personnelles.
3. Les créneaux importés de l'extérieur doivent être convertis en périodes d'indisponibilité non réservables sur Sweet-Home.
4. Respect de la vie privée : Seul l'état "Occupé" est synchronisé ; aucun détail (titre, lieu, description) des événements externes ne doit être affiché aux Homers ou stocké de manière lisible en BDD.
5. Système de rafraîchissement automatique des flux (background job) et option de synchronisation manuelle forcée depuis le tableau de bord.