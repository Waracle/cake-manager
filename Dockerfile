FROM openjdk:17
COPY --from=build /home/app/target/cake-manager-0.0.1-SNAPSHOT.jar /usr/local/lib/cake-manager-0.0.1-SNAPSHOT.jar
EXPOSE 8282
ENTRYPOINT ["java","-jar","/usr/local/lib/cake-manager-0.0.1-SNAPSHOT.jar"]
