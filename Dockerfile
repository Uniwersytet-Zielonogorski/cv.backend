FROM gradle:jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:21-jdk

EXPOSE 8080

RUN mkdir /app && \
    groupadd -r appgroup && useradd --no-log-init -r -g appgroup cvuser

COPY --from=build /home/gradle/src/build/libs/*.jar /app/cv.backend.jar

USER cvuser

WORKDIR /app

# Set the entry point to start the Java application
# Java 21 automatically respects container memory and CPU limits; no need for explicit JVM options for this
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "cv.backend.jar"]
