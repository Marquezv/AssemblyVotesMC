FROM maven:3.8.3-openjdk-17

RUN mkdir -p /app

COPY target/gateway-0.0.1-SNAPSHOT.jar /app/gateway.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/gateway.jar"]
