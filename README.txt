Cake Manager Micro Service (fictitious)
=======================================

A summer intern started on this project but never managed to get it finished.
The developer assured us that some of the above is complete, but at the moment accessing the /cakes endpoint
returns a 404, so getting this working should be the first priority.

Requirements:
* By accessing /cakes, it should be possible to list the cakes currently in the system. JSON would be an acceptable response format.

* It must be possible to add a new cake.

* It must be possible to update an existing cake.

* It must be possible to delete an existing cake.

Comments:
* We feel like the software stack used by the original developer is quite outdated, it would be good to migrate the entire application to something more modern. If you wish to update the repo in this manner, feel free! An explanation of the benefits of doing so (and any downsides) can be discussed at interview.

* Any other changes to improve the repo are appreciated (implementation of design patterns, seperation of concerns, ensuring the API follows REST principles etc)

Bonus points:
* Add some suitable tests (unit/integration...)
* Add some Authentication / Authorisation to the API
* Continuous Integration via any cloud CI system
* Containerisation

Notes from Phill
==========

Requires Java 20 to run and requires lombok annotations to be enabled on intelliJ.
I used TDD and 'The Double Loop' cycle which is described brilliantly here: https://jmauerhan.wordpress.com/talks/double-loop-tdd-bdd-done-right/
Flyway migration scripts run automatically on startup.

CICD
Github actions workflow is setup to automatically test and build the docker container. Then it is pushed to dockerhub which can be viewed here: https://hub.docker.com/r/phillipdenness1/cakemanager

Starting app
Pull from dockerhub
docker run --publish 8080:8080 phillipdenness1/cakemanager:latest

Build and run docker
Build the container `docker build --tag cakemanagerpd . `
Run the container `docker run --publish 8080:8080 cakemanagerpd`

Using docker-compose
run docker compose `docker-compose up`

Using mvn
run `mvn spring-boot:run`

Documentation
Swagger is on http://localhost:8080/swagger-ui/index.html

Next steps:
Add pagination and sorting to getAll endpoint
Extend CICD to deploy to cloud