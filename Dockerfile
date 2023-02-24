FROM openjdk:17-jdk-slim-buster

# Set the working directory to /app
WORKDIR /cake-manager

# Copy the build output from the host machine to the container
COPY build/libs/cake-manager-*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the command to start the Spring Boot application when the container starts
CMD ["java", "-jar", "app.jar"]