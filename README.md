# Contracts Management System

This is a full-stack technical assessment for Seventh Ray Consulting and Technology Private Limited. It consists of a Spring Boot backend, a Next.js frontend, and a PostgreSQL database.

## Technologies Used

*   **Frontend:** Next.js 14, React 18, Tailwind CSS, TypeScript, Jest, React Testing Library.
*   **Backend:** Java 17, Spring Boot 3.2, Spring Data JPA, Lombok, JUnit 5, Mockito.
*   **Database:** PostgreSQL.

## Prerequisites

*   Java 17
*   Maven 3.8+
*   Node.js 18+
*   PostgreSQL running locally or accessible via network.

## Setup Instructions

### 1. Database Setup

Ensure PostgreSQL is running locally on port `5432`. Create a database named `contracts_db`.

You can execute the provided SQL scripts manually in pgAdmin:
1. `database/schema.sql` (Creates tables)
2. `database/seed.sql` (Inserts sample data)

*Note: The backend has been configured to automatically generate the tables and insert the seed data upon startup to make testing easier.*

### 2. How to run the backend

The backend connects to PostgreSQL using the default credentials (`postgres`/`postgres`) on `localhost:5432` for a database named `contracts_db`.
You can override these by setting the following environment variables:
`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.

To run the backend, open a terminal and execute:

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

**Port Number:** The Spring Boot backend runs on **port 8081** (instead of the default 8080 to avoid common port conflicts). The REST API will be available at `http://localhost:8081/api/contracts`.

### 3. How to run the frontend

The frontend expects the backend API to be running at `http://localhost:8081/api`. This is pre-configured in `frontend/.env.local`.

To run the frontend, open a separate terminal and execute:

```bash
cd frontend
npm install
npm run dev
```

**Port Number:** The Next.js frontend runs on **port 3000**. The application will be accessible in your browser at `http://localhost:3000`.

## How to execute tests

### Backend Tests
The backend includes unit tests for the service layer and slice tests for the REST API controllers using an in-memory database configuration.

```bash
cd backend
mvn test
```

### Frontend Tests
The frontend includes component tests using Jest and React Testing Library.

```bash
cd frontend
npm test
```
