# 🏨 Hotel Service

## 📘 Overview

The **Hotel Service** is a core microservice in the Hotel Booking System. It manages hotel listings and room details, allowing CRUD operations and integrations with other services via Kafka. It also exposes well-documented APIs using Swagger/OpenAPI.

---

## 🚀 Features

### 🔹 Hotel Management
- Create a new hotel
- Update existing hotel info
- Fetch hotel by ID
- List all hotels

### 🔹 Room Management
- Add room to a hotel
- Update room details
- Get room by ID
- Get all rooms in a hotel

### 🔹 Tech Highlights
- Kafka-based integration for search indexing
- DTOs for input validation and clean API models
- Swagger UI for interactive API exploration

---

## 🧰 Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Apache Kafka**
- **Lombok**
- **Jakarta Validation**
- **Swagger (Springdoc OpenAPI)**
- **Gradle**

---

## 🧱 Architecture

### 📦 Modules

- **Controller Layer** – REST API endpoints
- **Service Layer** – Business logic
- **Repository Layer** – JPA interaction with PostgreSQL
- **Kafka Producer** – Publishes hotel/room data for indexing
- **DTOs & Mappers** – Clean separation of entity and transport models

---

## 📂 API Endpoints

### 🔹 Hotel APIs

| Method | Endpoint              | Description              |
|--------|-----------------------|--------------------------|
| POST   | `/api/hotels`         | Create a hotel           |
| GET    | `/api/hotels/{id}`    | Get hotel by ID          |
| GET    | `/api/hotels`         | Get list of all hotels   |
| PUT    | `/api/hotels/{id}`    | Update hotel info        |

### 🔹 Room APIs

| Method | Endpoint                         | Description                     |
|--------|----------------------------------|---------------------------------|
| POST   | `/api/rooms/hotel/{hotelId}`     | Add a room to a hotel           |
| GET    | `/api/rooms/hotel/{hotelId}`     | List rooms of a hotel           |
| GET    | `/api/rooms/{roomId}`            | Get room by ID                  |
| PUT    | `/api/rooms/{roomId}`            | Update room info (via hotelId)  |

---

## 📜 Swagger API Docs

Once the service is up, access Swagger UI at:

http://localhost:8082/swagger-ui.html
