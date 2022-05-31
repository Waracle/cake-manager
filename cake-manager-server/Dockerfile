FROM jed.ocir.io/axssefozft8h/jdk11:latest
ENV PORT 9081
#ENV CLASSPATH /opt/lib
EXPOSE 8080
COPY target/*.jar /opt/app.jar
WORKDIR /opt
CMD ["java", "-jar", "app.jar"]
