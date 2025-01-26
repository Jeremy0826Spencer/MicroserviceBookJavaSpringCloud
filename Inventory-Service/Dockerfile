# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar file into the container
COPY target/Inventory-Service-0.0.1-SNAPSHOT.jar /app/inventory-service.jar

# Expose the Location Service port
EXPOSE 8082

# Set environment variable for active Spring profile
ENV SPRING_PROFILES_ACTIVE=docker

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/inventory-service.jar"]