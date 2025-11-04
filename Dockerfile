# Stage 1: Build with Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies first (better caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Package the app
RUN mvn clean package -DskipTests

# Stage 2: Run with JDK
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/rest-live-demo-highscoreAPI-1.0-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 7007

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]