# URL Shortener Coding Task

## Task

Build a simple **URL shortener** in a ** preferably JVM-based language** (e.g. Java, Kotlin).

It should:

- Accept a full URL and return a shortened URL.
- Persist the shortened URLs across restarts.
- Allow a user to **customise the shortened URL** (e.g. user provides `my-custom-alias` instead of a random string).
- Expose a **decoupled web frontend** built with a modern framework (e.g., React, Next.js, Vue.js, Angular, Flask with templates). This can be lightweight form/output just to demonstrate interaction with the API. Feel free to use UI frameworks like Bootstrap, Material-UI, Tailwind CSS, GOV.UK design system, etc. to speed up development.
- Expose a **RESTful API** to perform create/read/delete operations on URLs.  
  → Refer to the provided [`openapi.yaml`](backend/openapi.yaml) for API structure and expected behaviour.
- Include the ability to **delete a shortened URL** via the API.
- **Have tests**.
- Be containerised (e.g. Docker).
- Include instructions for running locally.

## Rules

- Fork the repository and work in your fork. Do not push directly to the main repository.
- We suggest spending no longer than **6-8 hours**, but you can take longer if needed.
- **Commit often with meaningful messages.**
- Write tests.
- Use the provided [`openapi.yaml`](backend/openapi.yaml) as a reference.
- Focus on clean, maintainable code.
- AI tools (e.g., GitHub Copilot, ChatGPT) are allowed, but please **do not** copy-paste large chunks of code. Use them as assistants, not as a replacement for your own work. We will be asking.

## Deliverables

- Working code.
- Decoupled web frontend (using a modern framework like React, Next.js, Vue.js, Angular, or Flask with templates).
- RESTful API matching the OpenAPI spec.
- Tests.
- A git commit history that shows your thought process.
- Dockerfile.
- README with:
  - How to build and run locally.
  - Example usage (frontend and API).
  - Any notes or assumptions.

## Running the Application Locally
There are two ways to run this program. Either run it via Docker or run it locally via your IDE.

### Prerequisites
- Java 21
- Maven
- Node.js and npm
- Docker/Podman (for Docker method)

Docker:
1. Make sure you have Docker/Podman installed.
3. Navigate to the root directory for each end:
   1. Backend: `cd backend`
      1. Build and package the backend application: `mvn clean package -DskipTests`
4. Navigate to the root directory containing the `docker-compose.yml` file.
6. Start the containers: `docker-compose up`
7. The backend will be accessible at `http://localhost:8080` and the frontend at `http://localhost:3000`

IDE:
Backend:
1. Make sure you have Java 21 and Maven installed.
2. Clone the repository: `git clone <repository-url>`
3. Navigate to the backend directory: `cd backend`
4. Build the project: `mvn clean install`
5. Run the application: `mvn spring-boot:run`
6. The application will be accessible at `http://localhost:8080`

Frontend:
1. Make sure you have Node.js and npm installed.
2. Navigate to the frontend directory: `cd frontend`
3. Install dependencies: `npm install`
4. Run the frontend application: `npm run dev`
5. The frontend will be accessible at `http://localhost:3000`

Frontend navigation:
It is a simple frontend UI created using React. Just implements the required functionality that are requested in the task.
- To shorten a URL, enter the full URL and an optional custom alias, then click "Shorten URL".
- To view all shortened URLs, click on the "View All URLs" link.
- To delete a shortened URL, click the "Delete" button next to the URL in the list.
- To use a shortened URL, simply enter the alias in the browser's address bar.

## Evaluation Criteria
Testing endpoints can be done using curl or Postman. Here are some example curl commands:

`
curl -X POST http://localhost:8080/shorten \
-H "Content-Type: application/json" \
-d '{"fullUrl":"https://example.com/very/long/url","customAlias":"my-alias"}'
`

`
curl http://localhost:8080/my-alias
`

`
curl http://localhost:8080/urls
`

`
curl -X DELETE http://localhost:8080/my-alias
`
