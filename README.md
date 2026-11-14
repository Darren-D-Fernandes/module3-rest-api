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
- Docker Desktop (for containerized API execution)


## Operating Instructions

### 1. Running the Backend Server(Local Maven)
Open your terminal inside the project root folder (where `pom.xml` lives) and run:
```sh
mvn spring-boot:run
```
The REST API will boot and actively listen on: `http://localhost:8080`


### 2. Running the Backend Server (Docker — Phase 2A)
Ensure **Docker Desktop** is running and port `8080` is free locally. From the project root folder (where `Dockerfile` and `compose.yaml` live), run:

* **Build and start the API container:**
  ```sh
  docker compose up -d --build
  ```
* **Verify container status and view logs:**
  ```sh
  docker compose ps
  docker compose logs api
  ```
* **Stop and remove the API container:**
  ```sh
  docker compose down
  ```
The containerized REST API is accessible at `http://localhost:8080` (mapped to host port `8080` via `127.0.0.1:8080:8080`), including:
* Welcome route: `http://localhost:8080/api/hello`
* Ideas endpoint: `http://localhost:8080/api/ideas`


### 3. Running the Frontend Server
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

## Phase 2A: Docker Concepts & Reflection
* **What is a Docker image?**  
    A Docker image is basically a read-only, self-contained package/template that bundles the compiled application(`app.jar`), runtime environment(JRE), dependencies, and startup configurations needed to run the service across any machine.
* **What is a Docker container?**  
    Docker container is a live, isolated running instance created from the Docker image. They can be started, stopped and replaced independently without modifying the image.
* **Why did your POST data disappear after replacing the container?**  
    Right now the API stores ideas in a volatile RAM(`IdeaStore`) inside the Java process rather than an external storage or a Docker volume. Running the `docker compose down` and then `docker compose up -d` will terminate the old container and start a brand-new one clearing all local data and starting a new instance.

## Phase 2B: Docker API + SQLite
### Database Configuration & Schema
* **Local Database Path:** `data/ideas.db` (configured via `app.db-path=${DB_PATH:data/ideas.db}` in `application.properties`)
* **Docker Database Path:** `/app/data/ideas.db` (set via `DB_PATH=/app/data/ideas.db` in `compose.yaml`)
* **`ideas` Table Columns (`schema.sql`):**
  * `id` (`INTEGER PRIMARY KEY`) — auto-generated unique identifier
  * `title` (`TEXT NOT NULL CHECK(length(trim(title)) > 0)`) — non-blank idea title
  * `category` (`TEXT NOT NULL CHECK(length(trim(category)) > 0)`) — non-blank idea category

### How Persistence Works
* **Table Creation:** On startup, the `IdeaRepository` constructor creates the target parent directory (`Files.createDirectories`) and executes `CREATE TABLE IF NOT EXISTS ideas (...)` via SQLite JDBC without deleting existing records.
* **Docker Volume Preservation:** In `compose.yaml`, the named volume declaration (`volumes: ideas-data:`) and service mount (`ideas-data:/app/data`) store `/app/data/ideas.db` outside the container's temporary filesystem on the host machine.
* **Why Data Disappeared in Assignment 1 vs. Now:** In Assignment 1 (Phase 2A), ideas were stored in volatile RAM (`IdeaStore`), so replacing the container wiped all records. Now, ideas are written to SQLite inside the mounted `ideas-data` Docker volume, so records remain intact across `docker compose down` and `docker compose up -d`.
