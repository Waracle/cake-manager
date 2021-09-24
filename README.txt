Cake Manager Micro Service (fictitious)
=======================================


Solution delivered as Camel Spring Boot Rest Service Application
With integration tests against fully deployed application.

Github Actions script used for CI, Docker Image building and CD of image to DockerHub

GitHub

https://github.com/clarkregragui/cake-manager

DockerHub

https://hub.docker.com/repository/docker/clarkregragui/docker-cakes-manager-repo



TECHNOLOGIES

Maven
Spring Boot
Spring Data JPA
Apache Camel
    - Camel Rest
    - Camel Velocity
Spring Test
Junit5
Mockito
Docker



To run

mvn package

followed by

mvn spring-boot:run

URLs

Swagger

GET http://localhost:8080/api-doc


Velocity template HTML Cakes View

GET http://localhost:8080/

Pretty Print Json Cakes List

GET http://localhost:8080/cakes

Create new Cake with CakeDTO Json HttpRequest.Body

POST http://localhost:8080/cakes


ADDITIONAL CHANGES

- deleted incomplete legacy solution
- moved cakes.json into version control so that there's no remote dependency and changes to the file are versioned and tested.
- removed all the duplicates from cakes.json file.
- updated some of the photos.











