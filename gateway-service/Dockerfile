# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the application configuration file into the container
COPY src/main/resources/application.yml /app/application.yml

# Copy the packaged jar file into the container
COPY target/Gateway-0.0.1-SNAPSHOT.jar /app/gateway-service.jar

# Expose the Gateway Service port
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["sh", "-c", "java -jar /app/gateway-service.jar"]
