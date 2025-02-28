# Use official OpenJDK image as base
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file from the target folder (assumes you already built your Spring Boot app)
COPY target/API_Gateway-0.0.1-SNAPSHOT.jar /app/api-gateway.jar

# Expose the port that your API Gateway will run on
EXPOSE 8081

# Run the Spring Boot app when the container starts
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
