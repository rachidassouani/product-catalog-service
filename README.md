# Product Management REST API

This is a Spring Boot application that provides a REST API for managing products. It offers CRUD (Create, Read, Update, Delete) operations for products with additional features for performance and data integrity.

## Features

- CRUD operations for products
- Paginated product listing for improved performance
- API documentation with Swagger (OpenAPI)
- Unit tests
- Input validation for product fields
- DTO pattern implementation
- Request logging for all endpoints

## Tech Stack

- Java 17
- Spring Boot
- Maven
- H2
- Swagger (OpenAPI)

## Getting Started

### Prerequisites

- Java JDK 17

### Installation

1. Clone the repository:
   `git clone git@github.com:rachidassouani/product-catalog-service.git`


2. Navigate to the project directory:
   cd product-service

3. Run the application:
   ./mvnw spring-boot:run

The application should now be running on `http://localhost:8080`.

## API Documentation

The API documentation is available via Swagger UI. After starting the application, you can access it at:
`http://localhost:8080/swagger-ui/index.html`

## Input Validation

Input validation is implemented for the following fields when saving or updating products:
- Name: Maximum is 100 chars and shouldn't be blank
- Price: Minimum value is 0 — price cannot be negative.

## Logging

All incoming requests to the API endpoints are logged for monitoring and debugging purposes.

## Upcoming Features

- Product image storage
- Integration tests
- Integrate Flyway for automated database migrations
- Add CI/CD pipelines using GitHub Actions
- Dockerize the application for containerized deployment
- Deploy the application to AWS
