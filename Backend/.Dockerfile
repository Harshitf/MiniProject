# Step 1: Use official Eclipse Temurin JDK 21 (LTS) base image
FROM eclipse-temurin:21-jdk AS build

# Set working directory inside container
WORKDIR /Backend

# Copy Maven wrapper and project files (for caching dependencies)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (cached layer)
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the application source
COPY src src

# Build the application JAR file (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# Step 2: Create lightweight runtime image
FROM eclipse-temurin:21-jre

# Set working directory inside container
WORKDIR /Backend

# Copy JAR from build stage
COPY --from=build /Backend/target/*.jar app.jar

# Expose port (change if your app uses a different port)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
