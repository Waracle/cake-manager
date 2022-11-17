# get base image
FROM adoptopenjdk/openjdk8

# install dependencies
ENV APP_HOME=usr/app
WORKDIR $APP_HOME
COPY ./target/cake-manager-1.0.0-SNAPSHOT.jar /usr/app

# Run default command
CMD ["java" ,"-jar","cake-manager-1.0.0-SNAPSHOT.jar"]
