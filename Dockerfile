# Base image with Gradle and JDK 17
FROM gradle:7.6.0-jdk17 AS builder

# Set the working directory
WORKDIR /poker

# Copy project files into the container
COPY . .

# Build the project using Gradle wrapper
RUN gradle clean build --build-cache -x test

# Run the application
CMD ["java", "-jar", "/poker/build/libs/poker.jar"]