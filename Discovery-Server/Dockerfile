# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar file into the container
COPY target/Discovery-Server-0.0.1-SNAPSHOT.jar /app/discovery-server.jar

# Expose the Eureka Server port
EXPOSE 8761

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/discovery-server.jar"]

