# Build context
FROM gradle:7.6.0-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN ./gradlew build

# Deploy context
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build app/build/libs/recipant-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar", "--spring.profiles.active=prod"]