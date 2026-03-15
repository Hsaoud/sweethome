#!/bin/bash
# ==============================================================================
# Sweet-Home - Script d'orchestration (Simulation d'Agent IA Workflows)
# ==============================================================================

PROJECT_DIR=$(dirname "$(realpath "$0")")/..
TICKETS_DIR="${PROJECT_DIR}/tickets"
AGENTS_DIR="${PROJECT_DIR}/agents"
APP_DIR="${PROJECT_DIR}/sweet-home-app"

echo "================================================="
echo "  Démarrage de l'orchestrateur Sweet-Home"
echo "================================================="

CURRENT_TICKET="${TICKETS_DIR}/ticket-001-user-registration.md"

# 1. Lecture du ticket
if [ ! -f "$CURRENT_TICKET" ]; then
    echo "❌ Erreur : Le fichier $CURRENT_TICKET est introuvable."
    exit 1
fi
echo "✅ [ORCHESTRATEUR] Ticket chargé : $(basename "$CURRENT_TICKET")"

# 2. IA - Business Analyst
echo "⚙️  [AGENTS_LOOP] Appel au Business Analyst..."
sleep 1
echo "   -> Le BA traite $(basename "$CURRENT_TICKET"), clarifie l'ambiguïté et génère un MCD détaillé."

# 3. IA - QA Lead (TDD Enforced)
echo "⚙️  [AGENTS_LOOP] Appel au QA Lead..."
sleep 1
echo "   -> Le QA Lead rédige user-registration.spec.ts dans l'application Angular basé sur le ticket."
echo "❌ [TEST_STATUS] Exécution des tests : FAILED (Rouge). Motif : Code de production manquant."

# 4. IA - Developer
echo "⚙️  [AGENTS_LOOP] Appel au Developer..."
sleep 1
echo "   -> Le Developer traite les erreurs du test et crée user-registration.component.ts."
echo "✅ [TEST_STATUS] Exécution des tests : SUCCESS (Vert). Exit Code: 0"

# 5. IA - CTO
echo "⚙️  [AGENTS_LOOP] Appel au CTO pour contrôle des piliers..."
sleep 1
echo "✅ [CTO] Validation OK. TDD respecté. Maintenabilité assurée."

echo "================================================="
echo "  Fin de l'orchestration pour ce ticket."
echo "================================================="
