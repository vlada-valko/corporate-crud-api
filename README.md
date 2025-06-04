# :office: corporate-crud-api - RESTful API for Corporate Data Management
A Spring Boot-based RESTful API providing CRUD operations for managing corporate entities such as employees, departments, position, etc. 
## :interrobang: About the project
This project is a backend API that handles CRUD operations on corporate data. It is implemented using Java and Spring Boot, following REST architectural principles. The API supports operations like creating, reading, updating, and deleting employees, departments,  positions, etc. It utilizes Spring Data JPA for database interactions and includes validation, error handling, and layered service design for maintainability and scalability.
## :rocket: Features

- User authentication with JWT tokens
- Role-based access control (RBAC)
- Secure password storage (BCrypt)
- CRUD operations
- Structured DTOs for request/response handling
- Exception handling with detailed API responses
- Integrated with PostgreSQL
- Clean layered architecture (Controller → Service → Repository)

## :wrench: Technologies
* Java 21
* Spring Boot 3.4.0
* Spring Data JPA
* Spring Security 6
* Spring Web (REST API)
* Spring Validation
* Thymeleaf + Thymeleaf extras for Spring Security
* H2 Database (runtime, in-memory, for development)
* PostgreSQL (runtime, for production)
* Lombok (code generation)
* MapStruct (DTO and entity mapping)
* JWT (JSON Web Tokens for authentication)

  
## :rocket: How to Run
:one: Clone the repository

bash
git clone https://github.com/vlada-valko/corporate-crud-api.git

cd corporate-crud-api

:two: Set up the database
Make sure PostgreSQL is installed and running.

Create a new database (e.g. corporate_db) and update your credentials in:

src/main/resources/application.properties

Example:

spring.datasource.url=jdbc:postgresql://localhost:5432/corporate_db

spring.datasource.username=postgres

spring.datasource.password=your_password

:three: Run the application

You can use the built-in Maven Wrapper — no need to install Maven globally.

▶ On Linux/macOS:
bash
./mvnw spring-boot:run

▶ On Windows:
cmd
mvnw.cmd spring-boot:run

The server will start on:
http://localhost:8080
