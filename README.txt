Project Info
=====================

To run a server locally execute the following command:

`mvn spring-boot:run`

and access the following URL:

`http://localhost:8282/`


To build image

docker build -t waracle/cake-manager:1.0-SNAPSHOT .

To run image

docker run -d -p 8282:8282 waracle/cake-manager:1.0-SNAPSHOT 

The project loads some pre-defined data in to an in-memory database.

With
* Tests
* Continuous Integration via any cloud CI system
* Containerisation

No
* Authentication via OAuth2
