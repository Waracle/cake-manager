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

Scope
* Only the API and related code is in scope. No GUI of any kind is required


Original Project Info
=====================

To run a server locally execute the following command:

`mvn jetty:run`

and access the following URL:

`http://localhost:8282/`

Feel free to change how the project is run, but clear instructions must be given in README
You can use any IDE you like, so long as the project can build and run with Maven or Gradle.

The project loads some pre-defined data in to an in-memory database, which is acceptable for this exercise.  There is
no need to create persistent storage.


Submission
==========

Please provide your version of this project as a git repository (e.g. Github, BitBucket, etc).

A fork of this repo, or a Pull Request would be suitable.

Good luck!

Changes Made - to cover the given requirement:
==============================================

1.Upgraded the application to spring boot 2.7.9 and added required dependencies to run application as spring boot application.
2.Added rest controller as the handler for the api entry point and added other required classes and functionalities
  to create,update,delete and get the cakes from the system.
3.Handled the exceptional scenarios using global exception handler/controller advice to respond back
  to client with the right informations incase of exceptions.
4.Added required test cases to cover different scenarios and code coverage - used jacoco plugin
5.Used In memory h2 database for persisting the cake details.
6.Configured Continuous Integration using GitHub Actions with the help of ci.yml which contains jobs and steps for build


Tools and Technology Used:
--------------------------
1.Spring Boot
2.Java 11
3.IntelliJ Idea - Any IDE can be used
4.Github - Repository and Continuous Integration
4.Maven - Build
5.Spring Junit 5 and mockito - For unit testing
6.Docker

Build and Run command :
=======================
1. Go to the project folder
2. Run mvn clean install
3. After successful build run mvn spring-boot:run
4. Use base url http://localhost:8090/cakes to access api

Continuous Integration:
=======================
1. Used GitHub Actions to perform CI by configuring ci.yml file
2. Steps added in ci.yml file are :
   a. Checkout Code from git
   b. Setup Java version 17
   c. Setup maven
   d. Build using maven
   e. Build docker image and Push the docker image into Docker Hub repo

   Above Continuous Integration will be triggered only for either a push directly to master branch or a pull request to master branch.

