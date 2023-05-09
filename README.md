# CodeFellowship

CodeFellowship is a Spring Boot application that allows users to log in, sign up, and manage their profiles. It demonstrates the usage of Spring Boot with Thymeleaf, JPA, Postgres, and Spring Security.

## Application Overview

The application consists of the following main components:

1. `ApplicationUser` class: Represents a user with properties such as username, password, firstName, lastName, dateOfBirth, and bio.
2. `ApplicationUserRepository` interface: Extends `JpaRepository` to provide CRUD operations for `ApplicationUser` objects and a method to find a user by their username.
3. `ApplicationUserController` class: Handles user registration and login.
4. `SiteUserDetailsServiceImpl` class: Implements the `UserDetailsService` interface to load user-specific data during authentication.
5. `WebSecurityConfig` class: Configures Spring Security with custom settings for the application.

## Getting Started

These instructions will help you get the project up and running on your local machine for development and testing purposes.

### Prerequisites

Make sure you have the following software installed:

- Java JDK 8 or later
- Maven or Gradle
- PostgreSQL for the database

### Installing

1. Clone the repository:

    ```
    git clone https://github.com/CodrCam/codefellowship.git
    cd codefellowship
    ```

2. In `src/main/resources/application.properties`, configure the database connection URL, username, and password.

3. Build and run the project with Maven (`mvn spring-boot:run`) or Gradle (`gradle bootRun`).

## Usage

1. Access the application at `http://localhost:8080`.
2. Register as a new user by clicking the "Sign Up" link and filling in the registration form.
3. Log in with your newly created account.
4. You should now be able to view your username on every page and log out if desired.

## Time Spent on Coding Problem

- 0 hours on prep
- 1.5 hours on the build
- 1.5 hours total

## Built With

- [Spring Boot](https://spring.io/projects/spring-boot) - The web application framework
- [Thymeleaf](https://www.thymeleaf.org/) - The server-side Java template engine
- [JPA](https://spring.io/projects/spring-data-jpa) - The Java Persistence API for database operations
- [PostgreSQL](https://www.postgresql.org/) - The open-source relational database
- [Spring Security](https://spring.io/projects/spring-security) - Security framework for authentication and authorization

## Authors

- Cameron Griffin - [CodrCam](https://github.com/CodrCam)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details.
