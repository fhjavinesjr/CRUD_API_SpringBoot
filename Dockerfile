# Use an official OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /CRUD_API

# Copy Maven wrapper files first (for dependency caching)
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Install dependencies
RUN ./mvnw dependency:resolve

# Copy the entire project
COPY . .

# Build te Spring Boot app
RUN ./mvnw clean package -DskipTests

#Run the packaged Spring Boot app
CMD ["java", "-jar", "target/CRUD_API-1.0-SNAPSHOT.jar"]
