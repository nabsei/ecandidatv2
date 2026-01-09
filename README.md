# Projet eCandidat

Plateforme de candidature en ligne pour l'Université d'Occitanie

## Description

eCandidat est une application web complète permettant aux étudiants de soumettre leurs candidatures pour différentes formations universitaires. La plateforme offre une interface intuitive pour consulter les formations disponibles, créer des candidatures et suivre leur état.

## Architecture

Le projet suit une architecture **monorepo** avec séparation claire entre frontend et backend :

```
ecandidat/
├── backend/           # API Spring Boot
├── frontend/          # Application Angular
└── docker-compose.yml # PostgreSQL uniquement
```

## Technologies

### Backend

- **Spring Boot** 4.0.1
- **Java** 21
- **PostgreSQL** 17
- **Spring Security** + JWT
- **Spring Data JPA**
- **Maven**

### Frontend

- **Angular** 21+
- **TypeScript** 5.9+
- **Angular Material**
- **RxJS** 7.8+

## Installation

### Prérequis

- Java 21
- Node.js 20+
- Maven 3.9+
- Docker

### Lancement de l'application

```bash
# 1. Lancer PostgreSQL en Docker
docker-compose up -d

# 2. Lancer le backend
cd backend
mvn spring-boot:run

# 3. Lancer le frontend (dans un autre terminal)
cd frontend
npm install
ng serve
```

### Accès

- **Frontend:** http://localhost:4200
- **Backend API:** http://localhost:8080
- **Database:** localhost:5432

## Comptes de test

**Administrateur :**

- Email: `admin@univ-occitanie.com`
- Password: `univoccitanie`

**Utilisateur :**

- Email: `user@test.com`
- Password: `password`

## Structure du projet

### Backend (`/backend`)

```
backend/
├── src/main/java/co/simplon/ecandidat/
│   ├── config/         # Configuration Spring
│   ├── controller/     # REST Controllers
│   ├── dto/            # Data Transfer Objects
│   ├── entity/         # JPA Entities
│   ├── init/           # Initialisation données
│   ├── repository/     # Spring Data Repositories
│   └── service/        # Business Logic
└── src/main/resources/
    └── application.properties
```

### Frontend (`/frontend`)

```
frontend/src/app/
├── core/
│   ├── models/         # TypeScript interfaces
│   ├── services/       # Services Angular
│   ├── guards/         # Route guards
│   └── interceptors/   # HTTP intercepteurs
├── features/           # Pages/Composants métier
│   ├── home/
│   ├── login/
│   ├── register/
│   ├── formations/
│   ├── faq/
│   ├── dashboard/
│   └── admin-dashboard/
└── shared/             # Composants partagés
    ├── navbar/
    └── footer/
```

## Endpoints API principaux

### Authentification

- `POST /auth/login` - Connexion
- `POST /auth/register` - Inscription

### Formations

- `GET /api/formations` - Liste des formations
- `GET /api/formations/{id}` - Détails d'une formation
- `GET /api/formations/ufr/{ufr}` - Formations par UFR
- `GET /api/formations/montpellier` - Récupérer les formations depuis l'API Montpellier Open Data
- `POST /api/formations` - Créer une formation (admin)
- `PUT /api/formations/{id}` - Modifier une formation (admin)
- `DELETE /api/formations/{id}` - Supprimer une formation (admin)

### Candidatures

- `GET /api/candidatures/my` - Mes candidatures
- `GET /api/candidatures/{id}` - Détails d'une candidature
- `POST /api/candidatures` - Créer une candidature
- `PUT /api/candidatures/{id}` - Modifier une candidature
- `DELETE /api/candidatures/{id}` - Supprimer une candidature
- `GET /api/candidatures/admin/all` - Toutes les candidatures (admin)
- `PUT /api/candidatures/admin/{id}/status` - Mettre à jour le statut d'une candidature (admin)

## Licence

Ce projet est développé dans le cadre de la formation CDA.
