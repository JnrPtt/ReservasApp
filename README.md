# ReservasApp 🏨

A backend REST API built with Spring Boot 4 that simulates the reservation management system of a hotel. Developed following clean architecture principles, TDD and JWT-based security.

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 4.0.1 |
| Security | Spring Security + JWT (jjwt 0.12.6) |
| Persistence | Spring Data JPA + PostgreSQL |
| Testing | JUnit 5 + H2 (in-memory) |
| Containerization | Docker Compose |
| Build tool | Gradle |

---

## 📁 Project Structure

The project follows a **clean architecture** with vertical slicing by domain:

```
src/
├── main/java/com/jnrptt/reservasapp/
│   ├── reservas/
│   │   ├── api/              # Controllers & exception handlers
│   │   ├── application/      # Use cases & application services
│   │   ├── domain/           # Entities, value objects & business rules
│   │   └── infrastructure/   # JPA repositories & config
│   ├── security/             # JWT filter, auth config
│   └── shared/               # Shared domain & API components
└── test/
    └── reservas/
        ├── application/      # Use case tests
        └── domain/           # Domain logic tests
```

Each module is self-contained with its own `api`, `application`, `domain` and `infrastructure` layers, ensuring clear separation of concerns.

---

## ✨ Features

- **JWT Authentication** — secure login and token-based access to protected endpoints
- **Reservation management** — create, query and cancel reservations with full business rule validation
- **Reservation states** — `ACTIVA`, `CANCELADA`, `FINALIZADA`
- **Period validation** — automatic detection of overlapping reservations
- **Centralized error handling** — consistent error responses across the API
- **TDD approach** — domain and application logic developed test-first
- **Docker ready** — spin up the full stack with a single command

---

## 🚀 Getting Started

### Prerequisites

- Java 21
- Docker & Docker Compose (optional, recommended)

### Environment Variables

| Variable | Required | Default | Description |
|----------|----------|---------|-------------|
| `JWT_SECRET` | ✅ Yes | — | Minimum 32 characters |
| `JWT_EXPIRATION` | No | `3600000` ms | Token expiration time |

### Run with Docker (recommended)

```bash
git clone https://github.com/jnrptt/reservasapp.git
cd reservasapp
docker compose up --build
```

| Service | URL |
|---------|-----|
| API | `http://localhost:8080` |
| PostgreSQL | `localhost:5432` |

### Run locally

1. Start PostgreSQL and create the `reservas_db` database
2. Default connection settings in `application.properties`:
    - URL: `jdbc:postgresql://localhost:5432/reservas_db`
    - User: `reservas_user`
    - Password: `reservas_pass`
3. Export `JWT_SECRET` and start the app:

**Linux/Mac:**
```bash
export JWT_SECRET="secretsecretsecretsecretsecret12"
./gradlew bootRun
```

**Windows PowerShell:**
```powershell
$env:JWT_SECRET="secretsecretsecretsecretsecret12"
.\gradlew.bat bootRun
```

---

## 🔐 Authentication

All `/reservas` endpoints require a valid JWT token.

**1. Login:**
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "juan"}'
```

**Response:**
```json
{
  "token": "eyJ..."
}
```

**2. Use the token in subsequent requests:**
```
Authorization: Bearer <token>
```

---

## 📋 API Endpoints

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/auth/login` | ❌ | Authenticate and get JWT token |
| GET | `/reservas` | ✅ | List all reservations |
| GET | `/reservas/{id}` | ✅ | Get reservation by ID |
| POST | `/reservas` | ✅ | Create a new reservation |
| POST | `/reservas/cancelar?id={id}` | ✅ | Cancel a reservation |

---

## 💡 cURL Examples

**Create a reservation:**
```bash
curl -X POST http://localhost:8080/reservas \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "periodo": {
      "inicio": "2026-03-10T10:00:00",
      "fin": "2026-03-10T11:00:00"
    },
    "estado": "ACTIVA"
  }'
```

**List reservations:**
```bash
curl http://localhost:8080/reservas \
  -H "Authorization: Bearer <token>"
```

---

## 🧪 Running Tests

```bash
./gradlew test
```

Tests use an in-memory H2 database, no external dependencies required.

---

## 🏗️ Architecture Decisions

- **Clean architecture** — domain layer has zero dependencies on frameworks or infrastructure
- **TDD** — tests were written before implementation, starting from domain rules
- **Value objects** — `Periodo` encapsulates time range logic and overlap validation
- **DTOs** — request/response models are decoupled from domain entities
- **JWT stateless auth** — no server-side session storage

---

## 📄 License

MIT
