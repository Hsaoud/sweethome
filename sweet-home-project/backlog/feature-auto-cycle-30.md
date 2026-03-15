Titre : [Génération Automatisée et Signature Électronique des Contrats de Prestation]

Contexte : Suite à la mise en place de la facturation et des attestations fiscales (Cycle 12), il est désormais impératif de sécuriser juridiquement chaque intervention. Pour se conformer aux réglementations du service à la personne et protéger les deux parties contre d'éventuels litiges, la formalisation d'un contrat de prestation signé numériquement est l'étape de professionnalisation indispensable.

User Story : En tant qu'utilisateur (Homer ou Cleaner), je veux pouvoir signer électroniquement un contrat de prestation après chaque confirmation de réservation afin d'encadrer légalement l'intervention sans aucune contrainte administrative papier.

Règles Métier :
- Génération automatique d'un contrat au format PDF dès qu'une réservation passe au statut "Confirmé", incluant les données des deux parties, l'adresse d'intervention, le tarif horaire et les horaires prévus.
- Mise en place d'un workflow de signature électronique simple (consentement clic + horodatage certifié) pour le Homer et le Cleaner.
- Blocage de l'accès au "Check-in" (Cycle 15) tant que le contrat n'a pas été signé par les deux parties.
- Envoi automatique d'une copie du contrat signé par email aux deux utilisateurs et archivage sécurisé dans leur espace "Documents".
- Clause de résiliation automatique : si le contrat n'est pas signé 2 heures avant le début de la prestation, une alerte critique est envoyée au support client.