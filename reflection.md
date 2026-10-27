# Phase 2 Reflection - Campus Ideas Board
Darren D. Fernandes

### 1. The Request Journey
When a user inputs data and hits "Submit Entry" in the React UI, the event handler intercepts the action, runs local validation, and triggers an asynchronous `fetch()` POST request to `http://localhost:8080/api/ideas`. The Spring Boot backend maps this payload via `@PostMapping` inside `IdeaController.java`, processes the transaction within `IdeaStore`, and returns an HTTP status of `201 Created` with the newly assigned object containing its ID. React catches this resolved promise, updates its internal state array, and renders the updated list row seamlessly.

### 2. Props vs. State
- **State** represents local, mutable memory managed entirely internal to a component (e.g., tracking the text typed into form input fields inside `IdeaForm.jsx`). 
- **Props** are read-only configuration inputs passed down from a parent component to a child component (e.g., `App.jsx` passing down the array of `ideas` to `IdeaList.jsx` for passive looping and rendering).

### 3. Frontend vs. Backend Validation
- **Frontend validation** provides instant, real-time feedback inside the browser window, intercepting empty text inputs locally before expensive network requests hit the wires. 
- **Backend validation** acts as the final secure gatekeeper. It guarantees data integrity on the server side, explicitly throwing a `400 Bad Request` if malformed entries bypass the frontend layout via terminal or Postman scripts.

### 4. Bug Fixed
Fixed a variable declaration mismatch inside `IdeaForm.jsx` where the state setter was declared as `setCetegory` but executed down below as `setCategory`, resulting in an unhandled compilation error and a blank browser screen freeze. Correcting the spelling resolved the variable binding instantly.

### 5. Refresh vs. Backend Restart
- **Refreshing the browser** flushes out the active runtime memory of the React client application, forcing a fresh `GET /api/ideas` pull. However, since the Spring Boot application stays active, all rows are preserved in backend memory.
- **Restarting the backend server** completely flushes the volatile, in-memory collection list (`IdeaStore`), reverting the data layer back to an empty array slate (`[]`).
