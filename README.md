# ManagerResponsibility

_This microservice provides read-only access to managerial organizational responsibilities. It exposes which organizations a person (manager) is responsible for and allows lookup from both person and organization perspectives using a REST-ful resource hierarchy. The service consumes an externally owned database table and does not modify the underlying data._

## Service Description

The ManagerResponsibility service answers questions about managerial organizational responsibilities within Sundsvalls kommun:

- **Which organizations is a specific manager responsible for?** - Look up by person ID or login name to get a list of organization IDs the manager oversees.
- **Who is the manager responsible for a specific organization?** - Look up by organization ID to find the responsible manager(s).

### Data Model

The service returns `ManagerResponsibility` objects containing:

| Field | Description | Example |
|-------|-------------|---------|
| `personId` | The manager's unique person ID (UUID) | `35532a17-26a0-4438-970c-375465ff1aff` |
| `loginName` | The manager's login name | `joe01doe` |
| `orgList` | List of organization IDs the manager is responsible for | `["123", "456", "789"]` |

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

- **MS SQL database**
  - **Purpose:** Database where domain data is stored
  - **Repository:** External database managed by third party

Ensure that these services are running and properly configured before starting this microservice.

## API Documentation

Access the API documentation via Swagger UI:

- **Swagger UI:** [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

Alternatively, refer to the `openapi.yml` file located in the project's root directory for the OpenAPI specification.

## Usage

### API Endpoints

Refer to the [API Documentation](#api-documentation) for detailed information on available endpoints.

### Example Request

```bash
curl -X GET http://localhost:8080/{municipalityId}/organizations/{orgId}/manager-responsibilities
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
