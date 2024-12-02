# Use Eclipse Temurin 22 as the base image
FROM eclipse-temurin:22 as build

# Install Maven manually
RUN apt-get update && apt-get install -y maven

# Set the working directory
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# Use Amazon Corretto for runtime
FROM amazoncorretto:22

# Set the working directory for the runtime
WORKDIR /app

# Copy the built JAR file
COPY --from=build /app/target/*.jar app.jar

# Expose the application's port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
