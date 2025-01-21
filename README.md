Here's a template for a **README** file tailored to your project based on the details you've provided:

---

# University of Montpellier Campus Management System

This project is developed as part of the **Advanced Programming module** for the **University of Montpellier**. It is a web-based application designed to manage data related to the university's campuses, buildings, rooms, and teaching components. The system supports multiple user roles, such as admin, manager, and teacher, each with specific permissions.

## Table of Contents

1. [Project Description](#project-description)
2. [Technologies Used](#technologies-used)
3. [Features](#features)
4. [Setup and Installation](#setup-and-installation)
5. [Database Configuration](#database-configuration)
6. [Usage](#usage)
7. [System Architecture](#system-architecture)
8. [License](#license)

## Project Description

The **Campus Management System** is built to streamline the management of the University of Montpellier's infrastructure. This application allows users to manage data about:
- **Campuses**: Information on the different campuses across the university.
- **Buildings**: Data on the buildings located on each campus.
- **Rooms**: Details of rooms available within the buildings, including their capacity and usage.
- **Components**: Teaching components (departments, faculties, etc.) linked to the campuses.

The system includes role-based access control for different users, such as administrators, managers, and teachers, to perform CRUD operations and retrieve specific data.

## Technologies Used

- **Java EE** with **Spring Boot**
- **JPA (Hibernate)** for ORM and persistence
- **PostgreSQL** as the relational database
- **Docker** for containerization
- **Maven** for project management and build automation
- **GitHub Actions** for CI/CD

## Features

- **Role-Based Access Control**: Supports multiple user roles (Admin, Manager, Teacher) with varying access rights.
- **CRUD Operations**: Full Create, Read, Update, Delete functionality for campuses, buildings, rooms, and components.
- **Campus Management**: Manage multiple campuses across the University of Montpellier.
- **Building and Room Management**: Manage buildings and rooms within each campus, including capacity, type, and status.
- **Component Management**: Manage teaching components linked to buildings and campuses.
- **Query Support**: Ability to query for specific information, such as rooms in a specific building or buildings on a campus.
- **Distance Calculation**: Additional feature to calculate the distance between campuses or buildings.
- **Reports and Logs**: Track user activities and generate reports.

## Setup and Installation

### Prerequisites

- **JDK 22**
- **Maven 3.6+**
- **PostgreSQL 13+**
- **Docker** (optional for running PostgreSQL or the entire application in containers)

### Cloning the Repository

```bash
git clone https://github.com/spaghetttti/UM-app
cd UM-app
```

### Building the Application

```bash
mvn clean install
```

### Running the Application Locally

```bash
mvn spring-boot:run
```

The application will start at `http://localhost:8080`.

### Running with Docker

To run the application in a containerized environment:

1. Build the Docker image:
   ```bash
   docker build -t campus-management-app .
   ```

2. Run the Docker container:
   ```bash
   docker run -p 8080:8080 campus-management-app
   ```

3. You can optionally run PostgreSQL in a Docker container by using the  `docker-compose.yml`

4. Start both services with Docker Compose:

```bash
docker-compose up --build
```

## Database Configuration

Make sure your PostgreSQL database is set up before running the application.

**Example database configuration:**

```properties
# application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
spring.datasource.username=myuser
spring.datasource.password=secret
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## Usage

Once the application is running, navigate to `http://localhost:8080` in your browser. Log in with one of the following roles:
- **Admin**: Full control over all features.
- **Manager**: Limited to managing campuses, buildings, and rooms.
- **Teacher**: Access to components related to teaching and room bookings.

Users can perform CRUD operations, retrieve specific data, and generate reports based on the user role.

## System Architecture

- **Controller Layer**: Handles HTTP requests and delegates actions to services.
- **Service Layer**: Contains the business logic for managing campuses, buildings, rooms, and components.
- **Repository Layer**: Manages database interactions via JPA (Hibernate).
- **Entity Layer**: Represents the domain model, including `Campus`, `Building`, `Room`, and `Component`.

### Entity Relationships

- **Campus** has many **Buildings**.
- **Building** has many **Rooms**.
- **Room** is associated with a **Component** (optional).
