# ManagerResponsibility

_This microservice provides read-only access to managerial organizational responsibilities. It exposes which organizations a person (manager) is responsible for and allows lookup from both person and organization perspectives using a REST-ful resource hierarchy. The service consumes an externally owned database table and does not modify the underlying data. Results are filtered to only include managers who exist in the Employee service._

## Service Description

The ManagerResponsibility service answers questions about managerial organizational responsibilities within Sundsvalls kommun:

- **Which organizations is a specific manager responsible for?** - Look up by person ID or login name to get a list of organization IDs the manager oversees.
- **Who is the manager responsible for a specific organization?** - Look up by organization ID to find the responsible manager(s).

**Note:** All results are filtered against the Employee service to ensure only managers who exist in the employee registry are returned.

### Data Model

The service returns `ManagerResponsibility` objects containing:

|    Field    |                       Description                       |                Example                 |
|-------------|---------------------------------------------------------|----------------------------------------|
| `personId`  | The manager's unique person ID (UUID)                   | `35532a17-26a0-4438-970c-375465ff1aff` |
| `loginName` | The manager's login name                                | `joe01doe`                             |
| `orgList`   | List of organization IDs the manager is responsible for | `["123", "456", "789"]`                |

### Use Cases

- **HR systems** - Determine reporting structures and managerial chains
- **Access control** - Verify if a person has managerial responsibility for an organization
- **Organizational dashboards** - Display responsibility assignments

## Getting Started

### Prerequisites

- **Java 25 or higher**
- **Maven**
- **Git**

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/Sundsvallskommun/api-service-manager-responsibility.git
   cd api-service-manager-responsibility
   ```
2. **Configure the application:**

   Before running the application, you need to set up configuration settings.
   See [Configuration](#Configuration)

   **Note:** Ensure all required configurations are set; otherwise, the application may fail to start.

3. **Ensure dependent services are running:**

   If this microservice depends on other services, make sure they are up and accessible.
   See [Dependencies](#dependencies) for more details.

4. **Build and run the application:**

   ```bash
   mvn spring-boot:run
   ```

## Dependencies

This microservice depends on the following services:

- **MS SQL Server database**
  - **Purpose:** Read-only access to the `org_edw.vChefOrganisationer` view containing manager responsibility data
  - **Repository:** External database managed by third party
- **Employee API**
  - **Purpose:** Verify that managers exist in the employee registry. Results are filtered to only include managers who exist in the Employee service.
  - **Configuration:** Requires OAuth2 client credentials (see Configuration section)
  - **Features:** Circuit breaker pattern for resilience, cached responses (5-day TTL)

Ensure that these services are running and properly configured before starting this microservice.

## API Documentation

Access the API documentation via Swagger UI:

- **Swagger UI:** [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

Alternatively, refer to the OpenAPI specification file at `src/integration-test/resources/api/openapi.yaml`.

## Usage

### API Endpoints

The service provides three lookup endpoints, all returning a list of `ManagerResponsibility` objects:

|                                Endpoint                                |                         Description                         |
|------------------------------------------------------------------------|-------------------------------------------------------------|
| `GET /{municipalityId}/organizations/{orgId}/manager-responsibilities` | Find manager(s) responsible for a specific organization     |
| `GET /{municipalityId}/persons/{personId}/manager-responsibilities`    | Find responsibilities for a specific person (by UUID)       |
| `GET /{municipalityId}/logins/{loginName}/manager-responsibilities`    | Find responsibilities for a specific person (by login name) |

### Example Requests

**Find manager responsible for organization 123:**

```bash
curl -X GET http://localhost:8080/2281/organizations/123/manager-responsibilities
```

**Find responsibilities for a person by UUID:**

```bash
curl -X GET http://localhost:8080/2281/persons/35532a17-26a0-4438-970c-375465ff1aff/manager-responsibilities
```

**Find responsibilities for a person by login name:**

```bash
curl -X GET http://localhost:8080/2281/logins/joe01doe/manager-responsibilities
```

### Example Response

```json
[
  {
    "personId": "35532a17-26a0-4438-970c-375465ff1aff",
    "loginName": "joe01doe",
    "orgList": ["123", "456", "789"]
  }
]
```

## Configuration

Configuration is crucial for the application to run successfully. Ensure all necessary settings are configured in
`application.yml`.

### Key Configuration Parameters

- **Server Port:**

  ```yaml
  server:
    port: 8080
  ```
- **Database Settings:**

  ```yaml
  spring:
    datasource:
      url: jdbc:sqlserver://<server address>:<port>;databaseName=<database name>;trustServerCertificate=true
      username: <database user name>
      password: <database user password>
  ```
- **Employee API Integration:**

  ```yaml
  integration:
    employee:
      url: <employee service base url>
      connect-timeout: 5
      read-timeout: 20

  spring:
    security:
      oauth2:
        client:
          registration:
            employee:
              client-id: <oauth client id>
              client-secret: <oauth client secret>
          provider:
            employee:
              token-uri: <oauth token endpoint>
  ```
- **Caching Configuration:**

  The service uses Caffeine caching to reduce load on the Employee API:

  ```yaml
  spring:
    cache:
      type: caffeine
      cache-names: employeeExists
      caffeine:
        spec: expireAfterWrite=5d,maximumSize=3000
  ```

  - **TTL:** 5 days (employee existence is cached for 5 days)
  - **Max entries:** 3000 (least recently used entries are evicted when limit is reached)

### Database Initialization

- **No additional setup is required** for database initialization, as long as the database connection settings are
  correctly configured.

### Additional Notes

- **Application Profiles:**

  Use Spring profiles (`dev`, `prod`, etc.) to manage different configurations for different environments.

- **Logging Configuration:**

  Adjust logging levels if necessary.

## Contributing

Contributions are welcome! Please
see [CONTRIBUTING.md](https://github.com/Sundsvallskommun/.github/blob/main/.github/CONTRIBUTING.md) for guidelines.

## License

This project is licensed under the [MIT License](LICENSE).

## Code status

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-manager-responsibility&metric=alert_status)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-manager-responsibility)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-manager-responsibility&metric=reliability_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-manager-responsibility)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-manager-responsibility&metric=security_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-manager-responsibility)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-manager-responsibility&metric=sqale_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-manager-responsibility)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-manager-responsibility&metric=vulnerabilities)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-manager-responsibility)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-manager-responsibility&metric=bugs)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-manager-responsibility)

---

© 2026 Sundsvalls kommun
