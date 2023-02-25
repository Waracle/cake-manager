FROM maven:3.8.5-eclipse-temurin-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17
COPY --from=build /home/app/target/cake-manager-0.0.1-SNAPSHOT.jar /usr/local/lib/cake-manager-0.0.1-SNAPSHOT.jar
EXPOSE 8282
ENTRYPOINT ["java","-jar","/usr/local/lib/cake-manager-0.0.1-SNAPSHOT.jar"]
