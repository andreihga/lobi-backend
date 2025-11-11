FROM gradle:8.10.2-jdk21-alpine AS builder

# Set working directory
WORKDIR /app

# Copy Gradle files first for dependency caching
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon || return 0

# Copy the rest of the project and build it
COPY . .
RUN gradle bootJar --no-daemon

# Step 2: Run the app with a lightweight JRE image
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose Spring Boot port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]