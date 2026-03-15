# Architecture Initiale : Sweet-Home

## Stack Technologique
- **Frontend** : Framework Angular, tests automatisés avec Karma/Jasmine.
- **Backend / BDD** : Base de données relationnelle robuste sur PostgreSQL.
- **Déploiement cible** : Conteneurs Docker pour standardiser les environnements (Backend, Frontend, BDD).
- **Orchestration** : Scripts permettant l'intégration continue.

## Modèle de développement Workflow
La conception de nouvelles fonctions fonctionne via un pipeline modélisé s'inspirant des rôles de notre équipe :
`Ticket -> CEO (Validation) -> BA (Rédige le livrable absolu de spécifications dans \`product/specs/\` et les MCD) -> Architecte (MPD/Structure) -> QA Lead (Tests / spec.ts) -> Développeur (Implémente en prenant \`product/specs/\` comme référence absolue -> Exit code 0 -> Refactoring) -> CTO (Validation Globale)`.
