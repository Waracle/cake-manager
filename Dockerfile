FROM maven:3.6.3-openjdk-11 AS MAVEN_BUILD

ADD pom.xml ./
RUN mvn verify --fail-never 

COPY src/ ./src/

RUN mvn clean package

FROM openjdk:11.0.9-jre-slim-buster

COPY --from=MAVEN_BUILD /target/cake-manager-1.0-SNAPSHOT.jar /demo.jar

CMD ["java", "-jar", "/demo.jar"]

EXPOSE 8282