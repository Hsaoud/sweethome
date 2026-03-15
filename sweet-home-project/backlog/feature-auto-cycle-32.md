- Titre : [Système de Gestion des Frais Professionnels et Indemnités Kilométriques]
- Contexte : Suite à l'implémentation de l'optimisation des tournées et du calcul d'itinéraire (Cycle 31), la plateforme dispose désormais des données de navigation précises. La suite logique est d'automatiser le calcul et le suivi des frais de transport pour les Cleaners. Cela permet de transformer les données de distance en données comptables exploitables pour leurs déclarations fiscales et d'affiner le calcul de leur rentabilité réelle (Revenu Net - Frais).
- User Story : En tant que Cleaner, je veux que l'application calcule automatiquement mes indemnités kilométriques et me permette de saisir mes frais annexes afin de simplifier ma gestion administrative et d'optimiser ma fiscalité.
- Règles Métier : 
    1. Calcul automatique de la distance parcourue pour chaque mission (Trajet Domicile-Mission et Inter-Missions) basé sur les données de navigation.
    2. Application d'un barème kilométrique officiel (modifiable par l'administrateur) pour convertir les distances en montants financiers.
    3. Interface de saisie pour les frais professionnels annexes (parking, péages, achat de produits d'entretien) avec téléchargement obligatoire d'un justificatif (photo/PDF).
    4. Distinction claire dans le tableau de bord entre le chiffre d'affaires brut et le bénéfice net après déduction des frais calculés.
    5. Génération d'un export mensuel (CSV/PDF) récapitulant l'ensemble des frais et indemnités pour faciliter la déclaration de revenus ou la comptabilité.
    6. Option pour le Cleaner d'inclure automatiquement des "frais de déplacement" forfaitaires ou réels sur le devis/facture envoyé au Homer.