# Library Management System

A backend system built using **Spring Boot + MongoDB** that allows users to manage books, borrow/return them, and supports automated return policies.

---

# Features

- User Signup & Login
- Admin can add books
- Users can borrow books
- Each book can be borrowed only once
- Users can view borrowed books
- Users can return books
- Expiry-based auto return
- Library policy (auto return at 10 PM)
- MongoDB running in Docker
- REST APIs tested using Postman

---

# Architecture

```
        ┌──────────────┐
        │   Postman    │
        │ (API Client) │
        └──────┬───────┘
               │ REST APIs
               ▼
     ┌──────────────────────┐
     │   Spring Boot App    │
     │  (Business Logic)    │
     ├──────────────────────┤
     │ Controllers          │
     │ Services             │
     │ Scheduler            │
     │ Repositories         │
     └─────────┬────────────┘
               │
               ▼
     ┌──────────────────────┐
     │     MongoDB (Docker) │
     │   librarydb database │
     └──────────────────────┘
```

---

# Tech Stack

- **Java 21**
- **Spring Boot 3**
- **MongoDB**
- **Docker**
- **Gradle**
- **Postman (API Testing)**
- **Lombok**

---

# Prerequisites

Make sure the following are installed:

- ✅ Java 21
- ✅ Gradle (or use wrapper)
- ✅ Docker Desktop
- ✅ Postman

---

# Running MongoDB using Docker

Run the following command:

```bash
docker run -d -p 27017:27017 --name library-mongo mongo:7.0
```

Check if running:

```bash
docker ps
```

---

# Application Configuration

`src/main/resources/application.properties`

```properties
spring.application.name=demo
server.port=8083

spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration

spring.data.mongodb.uri=mongodb://localhost:27017/librarydb
```

---

# ▶ Running the Application

```bash
./gradlew bootRun
```

OR run from VS Code .

App will start at:

```
http://localhost:8083
```

---

# API Endpoints (Postman)

## 👤 User APIs

### Signup

```
POST /users/signup
```

### Login

```
POST /users/login
```

---

## 📚 Book APIs

### Add Book (Admin)

```
POST /books
```

### Get All Books

```
GET /books
```

---

## 🔁 Borrow / Return

### Borrow Book

```
POST /books/borrow?bookId=BOOK_ID&userId=USER_ID
```

### Return Book

```
POST /books/return?bookId=BOOK_ID&userId=USER_ID
```

---

## 📖 User Borrowed Books

```
GET /books/user/{userId}
```

---

# Scheduler Features

- Automatically checks expired books every 60 seconds
- Returns books after expiry time
- Returns "LIBRARY_ONLY" books at **10 PM daily**

---

# Testing

- All APIs tested using **Postman**
- Use MongoDB data to verify borrow/return operations

---

# Project Structure

```
src/
 ├── controller/
 ├── service/
 ├── repository/
 ├── model/
 ├── scheduler/
 └── config/
```

---

# Notes

- Each book can only be borrowed once
- Book availability is automatically managed
- Scheduler runs in background

---

# Future Improvements

- gRPC support
- Swagger API documentation
- Authentication with JWT
- Role-based access control

---

# Author

Developed as part of backend assignment.
