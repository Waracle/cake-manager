# Cake Manager Service

## About The Project


## Built With - Frameworks, Tools & technologies

* [JDK 11](https://jdk.java.net/11/)
* SpringBoot <version> - MicroService Implementation. It has been tested with two oAuth providers
    * [GitHub API](https://docs.github.com/en/rest)
* Spring Security - Securing the service and implementing zero trust model standards
* Mockito Spring boot & Junit - Unit & Component Testing
* Docker - for building java containers alternative to Google JIB
* Maven - Build Tool
* Jenkins & shared libraries for build and deploy
* OpenAPI/Swagger

## Prerequisites

* JDK 11
* Java IDE, you can use any IDE whichever you want, examples:
    * IntelliJ
    * Eclipse

## Generate access token
* Open up Postman and create a new POST request with Authorisation set to Basic, and use the following:
  * url: `https://dev-47242716.okta.com/oauth2/default/v1/token`
  * headers:
    * `accept: application/json`
    * `content-type:application/x-www-form-urlencoded`
  * Body:
    * `grant_type:client_credentials`
    *`scope:custom`
  * Username: `0oa5akb70onDcIo6a5d7`
  * Password: `w0dGDoitYco23G1sKsZqlnD1A1ZpNbct8kaKuUnu`

* Then Click Send and should get a json response with a user token which can be fed into the headers (e.g. `Authorization: Bearer eyJraWQiOi...KhA4B2NqfYQ` ) of cakes manager endpoints.

The Username/ Secrets should be kept in a Secrets Manager, but for demo purposes it is being exposed.

Note when invoking a POST request in Postman 

## Start Jenkins
* Start the jenkins container:
`docker-compose up -d`
 ### Initial Setup
* Get the initial admin password:
`docker exec jnkins cat /var/jenkins_home/secrets/initialAdminPassword`

* Confirm the jenkins container is running:
`docker ps`
* Navigate to localhost:8085 and enter admin password from above to unlock jenkins
* On the next page select `Install Suggested Plugins` and wait for the plugins to install

## Configure Jenkins pipeline

Steps to Create a simple Multibranch Pipeline Project

1. Click New Item in the top left corner on the Jenkins dashboard.
2. Enter the name of your code repo (Eg. cake-manager-server) in to enter an item
   name field, scroll down, and select Multibranch Pipeline and put "
   cake-manager-server" in copy from field.
3. Add a Branch Source and enter the location of the repository.
4. Enter the GitHub username, Password, ID, and Description
5. Save your details, and it will auto run to find all branches from git hub.

## Run back end Micro-service
* `cd cake-manager-server` 
* `mvn clean install` 
* ` mvn spring-boot:run`


Once micro-service is up and running, can view the swagger ui at `http://localhost:8080/swagger-ui/index.html#/`

## TODO:
* Create BBD module
* Create cake-manager-client module as an Angular project
* Improve on the Jenkins Pipeline, run unit tests, code quality
* Consider storing images on a S3 bucket

## About springboot micro service module

cake manager has been built as a multi-model app which contains below modules

* ***cake-manager-server***
    * this module is the back end micro service that exposes a rest api
* ***api***
    *  module will be responsible for Swagger, Api docs, Api models, auto generated models by
      protocol buffer and swagger utility
* ***cake-manager-client***
    * Angular Front end
  
#### Microservice packages roles & responsibility

Rename or define classes under package service, controller, client layer.

* Controller - classes for request and response transfer tasks to service package layer and map data
  in respective model
* Service - classes to execute or orchestration of business implementation or fetch data from other
  service/back end systems by calling client layer
* Client - classes to interact with backend service and play as a wrapper for all types of
  communication with backend system
* Repository - classes for all communication with the microservice database
* Config - classes for defining configuration and beans
* Constants - enum type constants
* Utils - microservice level utilities only

## Endpoints Available
* Swagger Ui:  http://localhost:8080/swagger-ui/index.html#/
* POST: /cakes
* GET: /cakes
* GET: /cakes/{id}

### To run BDD tests

* `gradle cucumber` to run all the tests on default env
* `gradle cucumber -Dtags=@<tag_name>` to run the specific tests with tags
* `gradle cucumber -Denv.type=buildenv ` to run all the tests on build environment
* `gradle cucumber -Denv.type=qaenv ` to run all the tests on QA/INT environment