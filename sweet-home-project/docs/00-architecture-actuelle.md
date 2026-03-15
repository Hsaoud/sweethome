# Architecture Actuelle - Module d'Authentification

Ce document décrit le fonctionnement et l'architecture du module de Login / Inscription existant dans `sweethome-back`.

## Composants
- **AuthController** : Expose les Endpoints REST.
- **AuthService** : Logique métier d'inscription.
- **UserService / CustomUserDetailsService** : Gestion des utilisateurs et de Spring Security (récupération de l'utilisateur par email).
- **AuthenticationManager** : Spring Security, gère la vérification des Credentials.

## Flux existants (BPMN simplifié)

### 1. Inscription (Homer / Cleaner)
```mermaid
sequenceDiagram
    participant Client
    participant AuthController
    participant AuthService
    participant UserRepository
    participant Database

    Client->>AuthController: POST /api/auth/register/{role}
    AuthController->>AuthController: Valide mot de passe (password == confirmPassword)
    AuthController->>AuthService: Vérifie existence email (emailExists)
    AuthService->>UserRepository: existsByEmail()
    UserRepository-->>AuthService: Boolean
    AuthService-->>AuthController: Boolean result
    
    alt Si email existe
        AuthController-->>Client: 409 Conflict
    else Si email disponible
        AuthController->>AuthService: register(Homer/Cleaner)
        AuthService->>AuthService: Hashage du mot de passe (PasswordEncoder)
        AuthService->>UserRepository: save() (via Homer/CleanerRepository)
        UserRepository->>Database: INSERT
        Database-->>UserRepository: Entité sauvegardée
        UserRepository-->>AuthService: Entité sauvegardée
        AuthService-->>AuthController: Entité sauvegardée
        AuthController-->>Client: 200 OK (Entité retournée)
    end
```

### 2. Connexion (Login)
```mermaid
sequenceDiagram
    participant Client
    participant AuthController
    participant AuthenticationManager
    participant UserService
    
    Client->>AuthController: POST /api/auth/login {username, password}
    AuthController->>AuthenticationManager: authenticate(Token)
    AuthenticationManager-->>AuthController: Authentication Object
    AuthController->>AuthController: SecurityContextHolder.setAuthentication()
    AuthController->>UserService: findByEmail()
    UserService-->>AuthController: User Entity
    AuthController-->>Client: 200 OK (User JSON)
```

### 3. Déconnexion (Logout)
- Endpoint : `POST /api/auth/logout`
- Action : Vide le contexte de sécurité (`SecurityContextHolder.clearContext()`).

## Points d'attention pour les Agents
- Le système utilise l'héritage JPA (`@Inheritance(strategy = InheritanceType.JOINED)`) pour séparer les `User` des `Homer` et `Cleaner`.
- Toute nouvelle information spécifique à un prestataire doit aller dans `cleaners`, et non dans `users`.
- Les mots de passe sont hashés en base (BCrypt assumé via Spring Security).
