# Campus Idea Board 

**Student Name:** Darren D. Fernandes  
**Course:** INEW 2434 Advanced Web Programming  
**Track:** Java Spring Boot Track + React Frontend Integration  

An interactive full-stack web application designed for students to propose, organize, and view campus clubs, academic ideas, and institutional enhancement events. This project bridges a functional React user interface with a validated, secure Java Spring Boot REST API backend layer.

## Project Architecture & URLs
The project is built as a split full-stack architecture where the user interface and data services run independently on separate local ports:
* **Frontend Client UI:** Serves dynamic user layouts via Vite on strict port allocations at: `http://localhost:5173`
* **Backend REST API:** Maps operations, handles validation, and serves JSON endpoints at: `http://localhost:8080`

### REST API Endpoints (`/api/ideas`)
* **GET `/api/ideas`**: Fetches all currently logged community submissions, returning an HTTP status of `200 OK`. If no records exist in active memory, an interactive empty-state message dynamically populates in the UI view.
* **POST `/api/ideas`**: Submits a new text entry containing a unique title and category. Valid text payloads generate an HTTP status of `201 Created` along with a database-assigned tracking ID number. Malformed, empty, or spaces-only titles are rejected by both client-side rules and server-side interceptors with an explicit status code error of `400 Bad Request`.

## Prerequisites
- Java Development Kit (JDK 25.0.2)
- Apache Maven
- Node.js LTS Runtime environment

## Operating Instructions

### 1. Running the Backend Server
Open your terminal inside the project root folder (where `pom.xml` lives) and run:
```sh
mvn spring-boot:run
```
The REST API will boot and actively listen on: `http://localhost:8080`

### 2. Running the Frontend Server
Open a second, concurrent terminal window, navigate into the frontend project directory, install local packages, and boot up the hot-reloading dev workspace:
```sh
cd frontend
npm install
npm run dev
```
The user interface will securely lock onto port allocations and serve at: `http://localhost:5173`

## Running Automated Test Suites

### Backend Tests
Execute core database memory unit checks and `MockMvc` endpoint routing simulations directly from the root project directory:
```sh
mvn test
```

### Frontend Tests
Execute isolated validation unit assertions checking for valid strings, empty entry rejections, and spaces-only blockages directly from the `frontend` folder:
```sh
node --test tests/validation.test.mjs
```

### Production Build Compilation
Compile and bundle static optimization deployment assets inside the `frontend` folder to guarantee codebase consistency:
```sh
npm run build
```

## Data Persistence Behavior
This integration phase utilizes a volatile, in-memory collection array store (`IdeaStore`). Data records are safely preserved during active client browser sessions and manual browser page refreshes. However, shutting down or restarting the Java application process completely flushes the volatile memory layer, resetting the data back to its default baseline state upon subsequent system loads.
