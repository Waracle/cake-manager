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

---
Notes from Phill
==========
## Contents
<p>
  <a href="#CICD">CICD</a> •
  <a href="#starting-app">Starting app</a> •
  <a href="#documentation">Documentation</a> •
  <a href="#authentication">Authentication</a> •
  <a href="#next-steps">Next steps</a>
</p>

## Methodology
I used TDD and 'The Double Loop' cycle which is described brilliantly here: https://jmauerhan.wordpress.com/talks/double-loop-tdd-bdd-done-right/

## CICD
* Github actions workflow is setup to automatically test and build the docker container. Then it is pushed to dockerhub which can be viewed here: https://hub.docker.com/r/phillipdenness1/cakemanager
* Flyway migration scripts run automatically on startup and populates the H2 DB with test data.

---
## Starting app

Choose 1 method below:
### Using docker-compose
```bash
# run docker compose 
docker-compose up
```
#### OR
### Pull from dockerhub
```bash
docker run --publish 8080:8080 phillipdenness1/cakemanager:latest
```
#### OR
### Build and run docker
```bash
# Build the container
docker build --tag cakemanagerpd .
# Run the container
docker run --publish 8080:8080 cakemanagerpd
```
#### OR

### mvn
```bash
mvn spring-boot:run
```
---
## Documentation

### Swagger documentation
 - http://localhost:8080/swagger-ui/index.html
 - Endpoints are validated jakarta.validation annotations

### Postman export
 - postman.cakemanager.json

## Monitoring
* Logback with Logstash encoder to support searchable logs
* Prometheus is available when running via docker-compose. 
  - Prometheus UI: http://localhost:9090/graph?
  - find_all_counter_total, find_by_id_counter_total, save_counter_total, update_counter_total, delete_counter_total, unknown_error_counter_total, bad_request_counter_total

## Authentication
* Basic authentication is applied to secure endpoints
  - Add header: `Authorization: Basic dXNlcjp1c2VyUGFzcw==`
  - Username=user , password=userPass

## Next steps
* Add pagination and sorting to getAll endpoint
* Extend CICD to deploy to cloud