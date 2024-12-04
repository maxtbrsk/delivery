# Use official OpenJDK 17 as base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /delivery

# Copy the Maven POM and source code
COPY pom.xml .
COPY src ./src

# Install Maven
RUN apk add --no-cache maven

# Package the application
RUN mvn clean package -DskipTests

# Use the JAR file
RUN mv target/*.jar app.jar

# Clean up Maven and source files to reduce image size
RUN apk del maven && \
    rm -rf src && \
    rm -rf target && \
    rm pom.xml

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]