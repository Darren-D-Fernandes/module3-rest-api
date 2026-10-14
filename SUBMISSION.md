# Phase 1 submission
Name: Darren D. Fernandes | 
Course or section: INEW 2434 Advanced Web Programming | 
Chosen track: Java Spring Boot

## My campus idea
A campus club and events suggestion platform for students wanting a change or something new to be held on campus.

## Evidence log
Record method, URL, status, and response. Attach screenshots or paste terminal output.

1. Personalized GET /api/hello:
    - **Method:** GET
    - **URL:** http://localhost:8080/api/hello
    - **Status:** 200 OK
    - **Response:** JSON object containing a welcome message with my name ("Darren")

2. GET /api/ideas before creating: 
    - **Method:** GET
    - **URL:** http://localhost:8080/api/ideas
    - **Status:** 200 OK
    - **Response:** A JSON array containing the initial 2 default starter seed ideas.
    - *(Screenshot attached as screenshots/test-a.png)*

3. Valid POST /api/ideas:
    - **Method:** POST
    - **URL:** http://localhost:8080/api/ideas
    - **Status:** 201 Created
    - **Response:** JSON object confirming the creation of the robotics club idea with assigned ID 3.
    - *(Screenshot attached as screenshots/test-b.png)*

4. GET /api/ideas after creating:
    - **Method:** GET
    - **URL:** http://localhost:8080/api/ideas
    - **Status:** 200 OK
    - **Response:** A JSON array containing 3 items total (the 2 seed ideas plus the newly added robotics club idea).
    - *(Screenshot attached as screenshots/test-c.png)*

5. Blank title POST (400) and unchanged list:
    - **Method:** POST
    - **URL:** http://localhost:8080/api/ideas
    - **Status:** 400 Bad Request
    - **Response:** Spring validation error response blocking the blank whitespace string input.
    - *(Screenshot attached as screenshots/test-d.png)*

6. Malformed JSON POST (400):
    - **Method:** POST
    - **URL:** http://localhost:8080/api/ideas
    - **Status:** 400 Bad Request
    - **Response:** Bad Request error indicating that the syntax structure of the incoming JSON body was malformed.

7. Restart the server and GET /api/ideas (two seed ideas again):
    - **Method:** GET
    - **URL:** http://localhost:8080/api/ideas
    - **Status:** 200 OK
    - **Response:** A JSON array displaying exactly 2 entries. This confirms that the volatile in-memory store completely reset when the application process restarted.

## Reflection
1. What does a controller do?
A controller's role is to route the incoming paths and map them to the correct java method. It enforces rules for the incoming requests and outgoing responses and once the backend finishes its tasks a controller wraps the results and sends back to the client, clean JSON text, along with an explicit HTTP status code badge(like 201 Created).

2. How is GET different from POST?
GET is used to request and read existing data from the server without changing anything. POST is used to send fresh data payloads into the server to insert/create a brand-new item. While both methods return a server response, GET expects a list or data view back, while POST expects a confirmation message showing that the new item was successfully added with an ID.

3. How do JSON title and category become Java data?
The controller intercepts the request and its @RequestBody annotation forces Spring Boot to parse that JSON into Java data object that can be then used by the methods.

4. Why does our added idea disappear after restarting?
The added idea disappers after restarting because we do not have a storage system built yet that will store the records, we are currently only working on the backend alone and using the memory of the program to store the data, hence when the server is shut down the data is lost.

5. What would the React frontend need to know to use this API?
For the React frontend to connect to this API it would need to know the base URL and Port, the Routes, Endpoints and the HTTP methods and it needs to know that we are sending and receiveing data in JSON.


## Debugging story
Symptom, hypothesis, change, and observed result:
Initially, I ran into an error stating that the 'mvn' command was not recognized in my terminal when trying to start up the Spring Boot server. My hypothesis was that Apache Maven wasn't linked to my laptop's system environment variables. I changed this by downloading standalone Maven, creating an administrative path folder, and adding the bin directory to my system environment Path variables; as an observed result, restarting VS Code unlocked the terminal and allowed `mvn spring-boot:run` to compile successfully. Additionally, while writing my automated test suites, I deliberately injected small syntax and assertion errors into the code to track the output and observe exactly when, where, and how the testing framework catches bugs.


## Assistance disclosure
People, documentation, or AI tools consulted; what I verified myself:
I referred to the notes and documentation provided by my professor to understand the project structure. For additional in-depth explanations of new concepts, framework functions and some syntax, I consulted with Gemini. I manually verified the code functionality myself by running automated tests in my terminal and checking the endpoint routes live in Postman.


## Automated test evidence
Command and summary: 
`mvn test` - This command compiled the test files and ran the automated suite. The terminal output displayed a green "BUILD SUCCESS" notification confirming that all 5 test hooks executed flawlessly with 0 failures, 0 errors, and 0 skips.

Two completed unit tests (names and assertions):
1. `addingIdeaStoresItsTitleAndCategory()`: Asserts that a created Idea request matches the input strings via `assertEquals()` and verifies that the data store actively saves the element using `assertTrue(store.findAll().contains(...))`.
2. `twoNewIdeasReceiveDifferentIds()`: Asserts that consecutive record inserts generate completely unique auto-incrementing ID sequences via `assertNotEquals(idea1.id(), idea2.id())` and checks that the total count correctly increments to a final size of 4 via `assertEquals(4, store.findAll().size())`.

Completed POST API test (name and assertions):
`validPostReturnsCreatedIdea()`: Used `MockMvc` to trigger an in-process mock HTTP POST network client request to `/api/ideas` with a raw JSON payload string. It asserts that the controller routes correctly by expecting an HTTP status code of `201 Created` via `status().isCreated()`, and verifies returning JSON content mapping structures via `jsonPath()`.

Explain why unit tests differ from Postman requests:
Unit tests evaluate core Java logic and data component classes(like constructors or array mutations) in complete isolation directly in the memory without loading web or network layers. Postman requests and MockMvc API tests actually test the whole API with web mapping interface, validating the HTTP headers, network status codes and tests if the controller routing rules are fully functional.

Confirm no required test is skipped or disabled:
Confirmed. Every `@Disabled` annotation and default template `fail()` placeholder marker has been completely removed from both test files. The final execution log confirms 0 skipped items.


