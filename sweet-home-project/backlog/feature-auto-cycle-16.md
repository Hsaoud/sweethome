- Titre : [Checklist de Prestation Personnalisée et Rapport de Mission]
- Contexte : Suite à l'implémentation du système de Check-in/Check-out et des preuves par photo (Cycle 15), il est crucial de structurer le travail du prestataire. La checklist permet de passer d'une simple validation de présence à une véritable garantie de qualité, en guidant le Cleaner sur les priorités spécifiques du Homer et en fournissant un compte-rendu d'exécution détaillé.
- User Story : En tant que Homer, je veux définir une liste de tâches précises et des consignes par pièce afin de m'assurer que mes attentes sont respectées et de recevoir un rapport de conformité structuré à la fin de l'intervention.
- Règles Métier :
    1. Le Homer peut configurer une checklist lors de la création de sa réservation (ex: "Nettoyer l'intérieur du micro-ondes", "Arroser les plantes du salon").
    2. Une bibliothèque de tâches standards (par catégorie : Cuisine, Chambre, etc.) est proposée par défaut.
    3. Le Cleaner doit obligatoirement valider ou commenter chaque point de la checklist avant de pouvoir valider son "Check-out".
    4. Pour les tâches marquées comme "Critiques" par le Homer, le système impose la prise d'une photo de preuve (lien direct avec la fonctionnalité du Cycle 15).
    5. Un rapport de mission PDF, incluant l'état de la checklist et les photos associées, est généré automatiquement et transmis au Homer dès la clôture de la prestation.