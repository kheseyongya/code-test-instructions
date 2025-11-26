# Use JDK 21 runtime
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy the built jar from Maven target
COPY target/shortenurl-0.0.1-SNAPSHOT.jar app.jar

# Create folder for H2 database
RUN mkdir -p /data

# Expose HTTP port
EXPOSE 8080

# Run the Spring Boot app, pointing H2 to container path
ENTRYPOINT ["java","-jar","app.jar","--spring.datasource.url=jdbc:h2:file:/data/urldb;MODE=PostgreSQL;DB_CLOSE_ON_EXIT=FALSE"]
