# syntax=docker/dockerfile:1

FROM maven:3.9.3-amazoncorretto-20 AS builder
WORKDIR /app
COPY pom.xml ./
COPY src ./src

RUN mvn clean install

# Second stage: Minimal runtime environment
FROM eclipse-temurin:20-jre-jammy
WORKDIR /app

# copy jar from the first stage
COPY --from=builder /app/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]