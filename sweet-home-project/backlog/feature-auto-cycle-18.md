- Titre : [Gestion des Modes d'Accès et Protocoles d'Entrée Sécurisés]
- Contexte : Suite à l'implémentation du Check-in/Check-out (Cycle 15) et des Checklists de mission (Cycle 16), la logistique d'accès au domicile est le dernier point de friction pour permettre une autonomie complète du Cleaner et une expérience "sans contact" pour le Homer.
- User Story : En tant que Homer, je veux configurer précisément les modalités d'accès à mon domicile (code, boîte à clés, gardien) afin que le prestataire puisse intervenir en toute sécurité même en mon absence.
- Règles Métier : 
    1. Le Homer doit pouvoir sélectionner un mode d'accès par défaut parmi : Présence physique, Boîte à clés (avec code), Digicode, Gardien/Concierge, ou Remise de clés préalable.
    2. Les informations sensibles (codes d'accès, emplacement exact des clés) doivent être chiffrées en base de données.
    3. Révélation Conditionnelle : Les détails d'accès ne sont rendus visibles sur l'application du Cleaner que 2 heures avant le début prévu de la prestation et uniquement si le statut est "Confirmé".
    4. Le Cleaner doit valider la bonne réception des instructions d'accès avant de pouvoir démarrer son trajet vers la mission.
    5. Possibilité pour le Homer d'ajouter une photo illustrative (ex: emplacement de la boîte à clés) qui suit les mêmes règles de visibilité restreinte.
    6. Après le Check-out, les informations d'accès redeviennent masquées pour le Cleaner afin de protéger la vie privée du Homer.