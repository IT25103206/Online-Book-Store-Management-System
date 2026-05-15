# Lumina Books - Premium E-Commerce Platform

A modern, full-stack book store application built with **Spring Boot** (Backend) and **Vanilla HTML/CSS/JS** (Frontend). This project demonstrates industry-standard software architecture and Object-Oriented Programming (OOP) principles.

---

## 🚀 Getting Started

### Prerequisites
* **Java 17** or higher
* **Maven** (for building the backend)
* **MySQL Server** (ensure a database named `book_db` exists)
* **VS Code** with *Live Server* extension (recommended for frontend)

### Backend Setup
1. Open the `Backend` folder in your IDE (IntelliJ or VS Code).
2. Open `src/main/resources/application.properties` and verify your MySQL credentials.
3. Run the Spring Boot application (`OopServerAppApplication.java`).
4. The server will start on `http://localhost:8080`.

### Frontend Setup
1. Open the `Frontend` folder.
2. Launch `index.html` using a local server (e.g., VS Code Live Server).
3. Access the application at the URL provided by the server (usually `http://127.0.0.1:5500`).

---

## 🧩 OOP Concepts Applied

This project is built using core Object-Oriented Programming principles to ensure scalability and maintainability:

### 1. Inheritance
Used in the Entity layer to manage different book types.
* **Super Class**: `Book.java` (contains common attributes like name, price, description).
* **Sub Classes**: `EBook.java` (adds `downloadUrl`) and `PrintedBook.java` (adds `weight`, `dimensions`).
* **Benefit**: Reduces code redundancy and allows for polymorphic behavior.

### 2. Abstraction
Used in the Service layer to separate definition from implementation.
* **Interface**: `BookService.java` defines the required operations.
* **Implementation**: `BookServiceImpl.java` contains the actual business logic.
* **Benefit**: Makes the system loosely coupled and easier to test/swap implementations.

### 3. Encapsulation
Applied throughout the project using Data Transfer Objects (DTOs) and Access Modifiers.
* **DTOs**: `BookDTO.java` encapsulates data being sent over the network.
* **Private Fields**: All entity and DTO fields are `private`, accessed via Getters and Setters (using Lombok).
* **Benefit**: Protects data integrity and hides internal implementation details.

### 4. Polymorphism
* **Method Overriding**: Used in the Service layer and Configuration classes (e.g., `WebConfig.java` overrides `addResourceHandlers`).
* **Dynamic Binding**: The system handles different book types (EBook/PrintedBook) through a single `Book` reference during processing.

---

## 🔗 Frontend-Backend Integration

### Communication Layer
The frontend communicates with the backend via **Asynchronous JavaScript (Fetch API)**.
* **Centralized API Utility**: Found in `Frontend/js/api.js`. This file contains all the fetch calls to the Spring Boot REST endpoints.
* **Authentication**: Managed via session-based tokens stored in `localStorage`.

### Image Storage Strategy
To prevent unwanted page refreshes during development (Live Server interference):
* Images are saved to an external path: `~/lumina_books/uploads/`.
* `WebConfig.java` maps the URL `/uploads/**` to this physical folder on your computer.

### Key Files
* `api.js`: The "bridge" between frontend UI and backend logic.
* `BookController.java`: Handles incoming HTTP requests and directs them to the service layer.

---

## 🛠 Tech Stack
* **Backend**: Spring Boot 3, Spring Data JPA, MySQL, Lombok.
* **Frontend**: Vanilla HTML5, CSS3 (Glassmorphism), JavaScript (ES6+).
* **Icons**: Font Awesome 6.

---

*Developed with ❤️ as a modern learning manuscript.*
