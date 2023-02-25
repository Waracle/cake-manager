FROM openjdk:17
COPY --from=build /home/app/target/cake-manager-0.0.1-SNAPSHOT /usr/local/lib/cake-manager-0.0.1-SNAPSHOT
EXPOSE 8282
ENTRYPOINT ["java","-jar","/usr/local/lib/cakemgr-0.0.1-SNAPSHOT.jar"]
