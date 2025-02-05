FROM openjdk:23-jdk-slim
WORKDIR /app
COPY build/libs/esc-notes-backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
