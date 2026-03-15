# Ticket : TCK-001 - Inscription Utilisateur et Vérification d'Identité

## Contexte
Afin d'initier la mise en relation entre prestataires de ménage et clients, nous devons disposer d'un système d'inscription sécurisé en accord avec le principe de confiance (Vision Blablacar).

## Spécifications Fonctionnelles / Critères d'Acceptation (AC)

1. **Formulaire d'Inscription :**
   - Champs requis : Nom, Prénom, Email, Mot de passe, Rôle (Client / Prestataire de ménage).
   - L'email doit être unique et comporter un format valide.

2. **Sécurité du Mot de Passe :**
   - Minimum 8 caractères.
   - Au moins une majuscule, une lettre minuscule et un chiffre, un caractère spécial.

3. **Vérification d'Identité (Pilier Confiance) :**
   - L'utilisateur doit uploader une pièce d'identité valide (Carte Nationale d'Identité ou Passeport).
   - Le profil créé devra avoir le statut "En attente de vérification".

4. **Contrainte TDD :**
   - La suite de tests associée côté composants métiers liés à l'inscription (Karma/Jasmine pour l'UI, tests d'API éventuels) doit être écrite en amont et doit échouer (red) avant tout développement.
