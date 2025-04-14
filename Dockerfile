FROM maven:3.8.6-openjdk-11-slim AS build

WORKDIR /app

# Copy the POM file first to leverage Docker cache
COPY pom.xml .
# Copy the Maven wrapper files
COPY .mvn/ .mvn/
COPY mvnw mvnw.cmd ./

# Download dependencies (this layer will be cached unless pom.xml changes)
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -B

# Copy source code
COPY src/ src/

# Build the application
RUN --mount=type=cache,target=/root/.m2 mvn package -DskipTests

# Use a smaller JRE image for the runtime
FROM openjdk:11-jre-slim

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
