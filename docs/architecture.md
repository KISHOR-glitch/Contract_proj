# Contracts Management System - Architecture Answers

## 1. How would you scale this system to support hundreds of thousands of contracts?

*   **Database:**
    *   **Indexing:** Ensure `status`, `owner_name`, and `title` are indexed (already implemented partially in `schema.sql`). For partial string matching on title/owner, we could use `pg_trgm` extension for GIN indexes to speed up `LIKE '%search%'` queries.
    *   **Pagination:** Rely on Keyset Pagination (Cursor-based) instead of Offset Pagination if the offset gets too large, as high offsets require scanning and discarding many rows.
    *   **Read Replicas:** Route read requests (like `GET /api/contracts`) to read replicas, and write requests (creating/updating contracts) to the primary database.
*   **Backend:**
    *   **Statelessness:** The Spring Boot application is stateless, allowing it to be horizontally scaled behind a load balancer (e.g., AWS ALB, NGINX).
    *   **Caching:** Introduce Redis or Memcached to cache frequently accessed data, like the contract details, if they don't change frequently. Cache invalidation would happen on update.
*   **Frontend:**
    *   Next.js App Router already does a good job, but we could utilize React Server Components for data fetching closer to the database and reduce client payload.

## 2. How would you improve search performance?

*   **Full-Text Search:** Instead of simple `LIKE` queries in PostgreSQL, use PostgreSQL's built-in Full-Text Search capabilities (`to_tsvector` and `to_tsquery`), which provide language-aware stemming and are much faster.
*   **Elasticsearch / OpenSearch:** For complex search requirements (fuzzy matching, highlighting, aggregations), offload search to a dedicated search engine. We would sync the PostgreSQL data to Elasticsearch using a tool like Debezium (CDC) or application-level events.
*   **Trigram Indexes:** If keeping search in Postgres, use `pg_trgm` for fast text similarity searching.

## 3. How would you secure the APIs?

*   **Authentication & Authorization:** Implement OAuth2 / OpenID Connect (e.g., Keycloak, Auth0, or AWS Cognito) or JWT-based authentication. Every API endpoint would require a valid Bearer token.
*   **Input Validation & Sanitization:** Ensure all inputs are validated at the controller level (using `@Valid`, which we have) and sanitize inputs to prevent SQL Injection (JPA/Hibernate handles this parameterization natively) and XSS (frontend should escape output).
*   **CORS:** Restrict CORS configurations to only allow requests from known frontend domains (implemented in `CorsConfig.java`).
*   **Rate Limiting:** Implement rate limiting (e.g., using Bucket4j or an API Gateway) to prevent DDoS attacks and brute force attempts.

## 4. How would you implement role-based access control?

*   **Roles:** Define standard roles like `USER` (can view their own contracts), `MANAGER` (can view/approve team contracts), and `ADMIN` (can view/edit all).
*   **Backend Implementation:**
    *   Use Spring Security's `@PreAuthorize` annotations on controller methods.
    *   Example: `@PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(authentication, #id)")`
*   **Database:** Link Users to Roles and Roles to Permissions. Ensure queries automatically filter based on the logged-in user's ID if they are a regular `USER`.
*   **Frontend Implementation:** Decode the JWT or use an auth context to conditionally render UI elements (e.g., hide the "Approve" button if the user is not a `MANAGER`).

## 5. What improvements would you make before releasing this feature to production?

*   **Observability:** Integrate comprehensive logging (SLF4J/Logback -> ELK stack or Datadog), metrics (Micrometer/Prometheus), and distributed tracing (OpenTelemetry/Zipkin).
*   **CI/CD:** Set up a robust pipeline (e.g., GitHub Actions, GitLab CI) to automatically run tests, build Docker images, and deploy to staging/production environments.
*   **Resilience:** Implement Circuit Breakers (Resilience4j) for external calls, and configure robust connection pooling (HikariCP) for the database.
*   **API Documentation:** Integrate Swagger / OpenAPI (springdoc-openapi) to automatically generate interactive API documentation.
*   **E2E Testing:** Add end-to-end tests using Cypress or Playwright for critical user journeys on the frontend.
