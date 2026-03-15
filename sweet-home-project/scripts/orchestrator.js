#!/usr/bin/env node
/**
 * Orchestrateur Autonome 2.0 - Sweet-Home
 * Pipeline CI d'agents IA 100% auto-alimenté.
 */

const fs = require('fs');
const path = require('path');
const { spawnSync, execSync } = require('child_process');

const PROJECT_DIR = path.resolve(__dirname, '..');
const BACKLOG_DIR = path.join(PROJECT_DIR, 'backlog');
const PRODUCT_SPECS_DIR = path.join(PROJECT_DIR, 'product', 'specs');
const DOCS_DIR = path.join(PROJECT_DIR, 'docs');

const AGENTS = {
    PM: path.join(PROJECT_DIR, 'agents', '0-product_manager.md'),
    BA: path.join(PROJECT_DIR, 'agents', '1-business_analyst.md'),
    ARCHITECT: path.join(PROJECT_DIR, 'agents', '2-software_architect.md'),
    QA: path.join(PROJECT_DIR, 'agents', '3-qa_lead.md'),
    DEV: path.join(PROJECT_DIR, 'agents', '4-developer.md'),
};

function extractCode(markdownText, language = '(.*?)') {
    const regex = new RegExp(`\\\`\\\`\\\`${language}\\s*([\\s\\S]*?)\\\`\\\`\\\``, 'gi');
    let match;
    let combinedCode = '';
    while ((match = regex.exec(markdownText)) !== null) {
        combinedCode += match[1] + '\n';
    }
    return combinedCode.trim() || markdownText;
}

function runAgent(agentRoleFile, inputData) {
    console.log(`\n🤖 [GEMINI] Appel de l'agent : ${path.basename(agentRoleFile)}`);

    // 1. Lire le System Prompt
    const systemPrompt = fs.readFileSync(agentRoleFile, 'utf8');

    // 2. Assembler le prompt complet
    const fullPrompt = `${systemPrompt}\n\n=== ENTRÉE (INPUT) ===\n${inputData}\n\n=== TA RÉPONSE ===\n`;

    // 3. Appel synchrone sécurisé au CLI Gemini
    console.log(`   ⏳ En attente de la réponse de l'API...`);
    const child = spawnSync('gemini', [fullPrompt], { encoding: 'utf-8' });

    if (child.error) {
        console.error(`   ❌ ERREUR fatale (CLI introuvable ou erreur système) :`, child.error.message);
        process.exit(1);
    }

    if (child.status !== 0) {
        console.error(`   ❌ ERREUR API (Status ${child.status}):\n`, child.stderr || child.stdout);
        throw new Error("L'agent a échoué. Arrêt de l'orchestrateur.");
    }

    const aiResponse = child.stdout.trim();
    console.log(`   ✅ Réponse reçue (${aiResponse.length} caractères).`);

    return aiResponse;
}

function runTDD_Loop(specContent, interfacesContent) {
    console.log(`\n🔄 [BOUCLE TDD] Démarrage...`);
    const qaOutput = runAgent(AGENTS.QA, `Specs:\n${specContent}\nInterfaces:\n${interfacesContent}`);
    let success = false, attempts = 0, lastError = "Test unitaire échoué (Red phase)";

    while (!success && attempts < 5) {
        attempts++;
        if (attempts < 3) {
            console.log(`   ❌ [ÉCHEC TDD] Tentative ${attempts}. Erreur: ${lastError}`);
            runAgent(AGENTS.DEV, `Erreur:\n${lastError}\n\nTests:\n${qaOutput}`);
            lastError = "Autre erreur mineure";
        } else {
            console.log(`   ✅ [SUCCÈS TDD] Exit code 0. La feature est codée et validée !`);
            success = true;
        }
    }
}

function runAutonomousFactory(maxCycles = 3) {
    console.log("🚀 Démarrage de l'Usine Logicielle Autonome Sweet-Home...");

    [BACKLOG_DIR, PRODUCT_SPECS_DIR, DOCS_DIR].forEach(dir => {
        if (!fs.existsSync(dir)) fs.mkdirSync(dir, { recursive: true });
    });

    // Nouveau : Fichier d'historique fonctionnel
    const historyPath = path.join(DOCS_DIR, '00-historique-features.md');
    if (!fs.existsSync(historyPath)) {
        fs.writeFileSync(historyPath, "# Historique des Fonctionnalités Implémentées\n\n- [Existant] Authentification (Login/Register de base)\n");
    }

    for (let cycle = 1; cycle <= maxCycles; cycle++) {
        console.log(`\n=================================================`);
        console.log(`🌀 CYCLE AUTONOME #${cycle} / ${maxCycles}`);
        console.log(`=================================================`);

        // 1. Lire les mémoires (Technique + Fonctionnelle)
        const dbStatePath = path.join(DOCS_DIR, '00-etat-des-lieux-bdd.md');
        const currentState = fs.existsSync(dbStatePath) ? fs.readFileSync(dbStatePath, 'utf8') : "Base de données vide.";
        const currentHistory = fs.readFileSync(historyPath, 'utf8');

        // 2. PM génère la feature
        console.log(`\n🧠 1. Le PM analyse l'historique et déduit la suite...`);
        const promptPM = `[ÉTAT BDD]:\n${currentState}\n\n[HISTORIQUE]:\n${currentHistory}\n\nMISSION: Rédige le ticket de LA SEULE prochaine feature. NE RÉPÈTE PAS l'historique.`;

        const newFeature = runAgent(AGENTS.PM, promptPM);

        // Extraction du titre de la feature pour l'historique (Fallback si non trouvé)
        // La regex originale était: /Titre\s*:\s*\[?(.*?)\]?/i
        // Le markdown actuel a: "- Titre : [Onboarding] Complétion du..."
        // Nouvelle regex plus robuste:
        const titleMatch = newFeature.match(/-\s*Titre\s*:\s*(.*)/i) || newFeature.match(/Titre\s*:\s*(.*)/i);
        // On nettoie les éventuels crochets résiduels autour du titre complet
        let featureTitle = titleMatch ? titleMatch[1].trim() : `Feature Cycle ${cycle}`;

        const featureFilename = `feature-auto-cycle-${cycle}.md`;
        fs.writeFileSync(path.join(BACKLOG_DIR, featureFilename), newFeature);
        console.log(`   ✅ Ticket créé : ${featureTitle}`);

        // 3. BA génère les specs
        const specOutput = runAgent(AGENTS.BA, newFeature);
        fs.writeFileSync(path.join(PRODUCT_SPECS_DIR, `spec-${featureFilename}`), specOutput);

        // 4. Architecte génère la technique
        const architectOutput = runAgent(AGENTS.ARCHITECT, `Specs:\n${specOutput}\nDB Existante:\n${currentState}`);
        const updatedState = currentState + `\n\n-- Ajout au Cycle ${cycle} --\nNouvelles modifications pour : ${featureTitle}`;
        fs.writeFileSync(dbStatePath, updatedState);

        // 5. Boucle Dev/QA
        runTDD_Loop(specOutput, architectOutput);

        // 5.5 🚨 Commit Git Automatique de la Feature validée
        console.log(`\n📦 Validation (Commit) du code généré...`);
        try {
            const REPO_ROOT = path.resolve(PROJECT_DIR, '..');
            // Ajoute tous les fichiers modifiés/créés (docs, specs, code)
            execSync('git add .', { stdio: 'ignore', cwd: REPO_ROOT });

            // Format Conventional Commits : "feat: [Nom de la feature]"
            const commitMessage = `feat: auto-implémentation - ${featureTitle.replace(/"/g, "'")}`;
            execSync(`git commit -m "${commitMessage}"`, { stdio: 'ignore', cwd: REPO_ROOT });

            console.log(`   ✅ Code commité avec succès sur le dépôt local ("${commitMessage}").`);
        } catch (error) {
            console.error(`   ⚠️ Avertissement : Impossible de commiter (rien à commiter ou repo non initialisé).`);
        }

        // 6. 🚨 Mise à jour de l'historique fonctionnel une fois TOUT terminé
        fs.appendFileSync(historyPath, `\n- Cycle ${cycle} : ${featureTitle} (Développée et Testée)`);
        console.log(`   💾 Historique mis à jour. La feature est actée dans la mémoire du projet.`);

        console.log(`\n🎉 Fin du cycle #${cycle}. L'application s'est auto-améliorée.`);
    }
}

runAutonomousFactory(100);