# Base image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the application JAR file to the container
COPY target/my-spring-boot-app.jar app.jar

# Expose the port on which the app runs
EXPOSE 8085

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]