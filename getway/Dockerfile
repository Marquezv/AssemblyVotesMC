FROM maven:3.8.3-openjdk-17

RUN mkdir -p /app

COPY target/getway-0.0.1-SNAPSHOT.jar /app/getway.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/getway.jar"]
