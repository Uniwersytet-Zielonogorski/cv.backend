# Use the OpenJDK image from Docker Hub as the base image
FROM openjdk:21-jdk

# Copy the JAR file into the container
COPY build/libs/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java","-jar","/app.jar"]
