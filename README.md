# Campus Idea Board API - Phase 1

**Student Name:** Darren D. Fernandes  
**Course:** INEW 2434 Advanced Web Programming  
**Track:** Java Spring Boot Track  

A Java Spring Boot backend API designed to handle student-submitted campus club and event suggestions. This project serves as an organized, validated, and fully tested backend data provider.

## How to Set Up and Run

1. Ensure you have Java JDK 17 or higher and Apache Maven configured on your computer path variables.
2. Open your terminal in the root directory of this project folder (where `pom.xml` lives).
3. Execute the startup command to launch the live backend server:
   ```sh
   mvn spring-boot:run
   ```
4. The backend server will boot up and host locally on port 8080. You can verify the server connection by visiting the personalized greeting endpoint at: `http://localhost:8080/api/hello`.

## Running Automated Test Suites

To execute the automated safety net validations—including core memory unit tests and `MockMvc` API endpoint routing simulations—open a separate terminal window and run:
```sh
mvn test
```

## API Endpoint Documentation

This backend exposes the following endpoint mappings under the base request URL path `/api/ideas`:

* **GET `/api/ideas`**: Fetches and reads the full array list of current campus ideas. It loads initially with two default seed suggestions and returns an HTTP status code of `200 OK`.
* **POST `/api/ideas`**: Intercepts an incoming raw JSON payload body containing a `"title"` and a `"category"`. It automatically converts the keys into Java data structures, appends the submission into the in-memory array list store, and returns an HTTP status code of `201 Created` along with its uniquely assigned tracking ID number.
  - *Data Contract & Validation:* String inputs are strictly evaluated. Requests will be blocked and return an explicit `400 Bad Request` status code error if any field is left blank, if titles exceed 80 characters, or if categories exceed 30 characters.