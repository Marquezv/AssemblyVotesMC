FROM maven:3.8.3-openjdk-17

RUN mkdir -p /app

COPY target/session-api-0.0.2-SNAPSHOT.jar /app/session-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/session-api.jar"]
