FROM openjdk

WORKDIR /app

COPY target/session-api-0.0.1-SNAPSHOT.jar /app/session-api.jar

ENTRYPOINT ["java", "-jar", "session-api.jar"]
EXPOSE 8081