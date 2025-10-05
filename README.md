### Auth Service (Spring Boot 3)

A simple authentication and customer lookup service built with Spring Boot 3.x (Jakarta), using H2 for local development and Springdoc for OpenAPI/Swagger UI.

### How to run

```bash
mvn spring-boot:run
```

App starts by default on `http://localhost:8080`.

### Endpoints

- **POST** `/api/auth/login`
- **GET** `/api/customers/{username}`

### API Docs / Consoles

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **H2 Console**: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:authdb`
  - Username: `sa`
  - Password: (blank)

### Sample requests

#### Login

```bash
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "alice",
    "password": "password"
  }'
```

#### Get customer by username

```bash
curl -X GET "http://localhost:8080/api/customers/alice" \
  -H "Accept: application/json"
```

### Notes

- For local development only. Do not store real passwords in plain text.
- Uses layered architecture (controller → service → repository) and DTOs for API payloads.

