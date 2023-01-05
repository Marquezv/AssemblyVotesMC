FROM maven:3.8.3-openjdk-17

RUN mkdir -p /app

COPY target/user-api-0.0.1-SNAPSHOT.jar /app/user-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/user-api.jar"]
