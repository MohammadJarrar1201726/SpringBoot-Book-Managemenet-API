# SpringBoot-Book-Managemenet-API
This Spring Boot application manages books and authors, offering endpoints for creating, reading, updating, and deleting (CRUD) their records. It integrates PostgreSQL as the primary database while using an H2 in-memory database for unit and integration testing.


---

## 🚀 Key Highlights

- Manage books and authors via RESTful APIs
- PostgreSQL as the primary database
- H2 in-memory database for testing
- Built with Spring Boot, Maven, and tested with JUnit


## 📂 Features Overview

### Books
- Add, update (full/partial), and delete books
- Retrieve a single book by ISBN or list all books

### Authors
- Add, update (full/partial), and delete authors
- Retrieve a single author by ID or list all authors

---

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven
- PostgreSQL


  ---

## ⚙️ Setup & Installation

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/MohammadJarrar1201726/SpringBoot-Book-Managemenet-API
```

### 2️⃣ Configure Database
Create a PostgreSQL database named `book_author_db` and update credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/book_author_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3️⃣ Build & Run
```bash
mvn clean install
mvn spring-boot:run
```
Application runs at: **`http://localhost:8081`**

---

---

## 🔗 API Reference

### 📖 Book Endpoints
| Method | Endpoint                | Description                |
|--------|-------------------------|----------------------------|
| PUT    | `/books/{isbn}`         | Create or update book      |
| GET    | `/books`                | Get all books              |
| GET    | `/books/{isbn}`         | Get book by ISBN           |
| PATCH  | `/books/{isbn}`         | Partial update book        |
| DELETE | `/books/{isbn}`         | Delete book                |

### ✍️ Author Endpoints
| Method | Endpoint                | Description                |
|--------|-------------------------|----------------------------|
| POST   | `/authors`              | Create author              |
| GET    | `/authors`              | Get all authors            |
| GET    | `/authors/{id}`         | Get author by ID           |
| PUT    | `/authors/{id}`         | Full update author         |
| PATCH  | `/authors/{id}`         | Partial update author      |
| DELETE | `/authors/{id}`         | Delete author              |



