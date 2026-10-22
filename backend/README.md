# Spring Boot Demo API

A simple Spring Boot application that demonstrates:
- clean package-by-feature structure
- REST API development with validation
- database persistence using SQL Server (local DB: TESTDB)
- transactional service layer
- request/response logging to `logs/application.log`
- paginated GET APIs
- API integration that calls a third-party REST endpoint

## Project Structure

```text
springboot-demo/
├── pom.xml
├── README.md
├── logs/
├── postman/
│   ├── SpringBootDemo.postman_collection.json
│   └── SpringBootDemo.postman_environment.json
└── src/
    └── main/
        ├── java/com/example/demo/
        │   ├── DemoApplication.java
        │   ├── config/
        │   │   ├── RequestResponseLoggingFilter.java
        │   │   └── RestTemplateConfig.java
        │   ├── controller/
        │   │   ├── CustomerController.java
        │   │   ├── ExternalApiController.java
        │   │   └── GlobalExceptionHandler.java
        │   ├── dto/
        │   │   ├── CustomerRequest.java
        │   │   ├── CustomerResponse.java
        │   │   └── PageResponse.java
        │   ├── model/
        │   │   └── Customer.java
        │   ├── repository/
        │   │   └── CustomerRepository.java
        │   └── service/
        │       └── CustomerService.java
        └── resources/
            └── application.properties
```

## Database Setup

1. Install Microsoft SQL Server or SQL Server LocalDB.
2. Create a database named `TESTDB`.
3. Use the credentials in `application.properties`:
   - Username: `sa`
   - Password: `YourStrong!Passw0rd`
4. If your SQL Server is not set to `localhost:1433`, update `spring.datasource.url` in `src/main/resources/application.properties`.

The application uses JPA with `ddl-auto=update`, so the `customers` table will be created automatically when the app starts if the database is available.

## Run the Application

```bash
cd springboot-demo
mvn spring-boot:run
```

The app starts on:
- http://localhost:8080

## Available APIs

### Product maintenance
- POST `/api/products`
- PUT `/api/products/{id}`
- GET `/api/products`
- GET `/api/products/{id}`
- DELETE `/api/products/{id}`

Example create request:
```json
{
  "name": "Laptop",
  "description": "14 inch business laptop",
  "quantity": 5,
  "price": 1200.50
}
```

### Create customer
- POST `/api/customers`

Request body:
```json
{
  "name": "Alice Johnson",
  "email": "alice@example.com",
  "status": "ACTIVE"
}
```

### Update customer
- PUT `/api/customers/{id}`

### Get customer by id
- GET `/api/customers/{id}`

### Get customers with pagination
- GET `/api/customers?page=0&size=10`

Note: each page returns up to 10 records by default.

### Third-party nested call example
- GET `/api/external-demo?page=1&size=10`

This endpoint calls the JSONPlaceholder API internally and returns the response to the client.

## Logging

Request and response details are logged through `RequestResponseLoggingFilter` and saved to:

```text
logs/application.log
```

Each request logs:
- HTTP method
- URI
- request headers
- request payload
- response status
- response payload

## Postman

Use the collection in:
- `postman/SpringBootDemo.postman_collection.json`

Use the environment file in:
- `postman/SpringBootDemo.postman_environment.json`

Import both into Postman and set the `baseUrl` variable to `http://localhost:8080`.

## Transactional Notes

`@Transactional` is implemented in `CustomerService` for:
- create customer
- update customer
- read customer
- read all customers (read-only transaction)

This ensures consistency and proper database transaction handling.
