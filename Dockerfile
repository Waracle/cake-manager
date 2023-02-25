FROM openjdk:17

ADD ./target/cake-manager.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/cake-manager.jar"]

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8282/actuator/health || exit 1

EXPOSE 8282