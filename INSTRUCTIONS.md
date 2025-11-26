
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
- To shorten a URL, enter the full URL and an optional custom alias, then click "Shorten".
- To use a shortened URL, simply enter the alias and hit "Get URL" which navigates you to the full url.
- To delete a shortened URL, simply enter the alias and hit the "Delete Url" button.
- To view all shortened URLs, click on the "View All URLs" link.

![Screenshot 2025-11-26 at 14.30.03.png](asset/Screenshot%202025-11-26%20at%2014.30.03.png)

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
