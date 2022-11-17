Cake Manager Micro Service (fictitious)
=======================================

A summer intern started on this project but never managed to get it finished.
The developer assured us that some of the above is complete, but at the moment accessing the /cakes endpoint
returns a 404, so getting this working should be the first priority.

Requirements:
* By accessing the root of the server (/) it should be possible to list the cakes currently in the system. This must be presented in an acceptable format for a human to read.

* It must be possible for a human to add a new cake to the server.

* By accessing an alternative endpoint (/cakes) with an appropriate client it must be possible to download a list of
the cakes currently in the system as JSON data.

* The /cakes endpoint must also allow new cakes to be created.

Comments:
* We feel like the software stack used by the original developer is quite outdated, it would be good to migrate the entire application to something more modern.
* Would be good to change the application to implement proper client-server separation via REST API.

Bonus points:
* Tests
* Authentication via OAuth2
* Continuous Integration via any cloud CI system
* Containerisation


Original Project Info
=====================

To run a server locally execute the following command:

1# Run CakeManagerApplication class in any IDE will start the API from IDE.
2#
    a. Compile application using 'mvn clean install' command.
    b. run jar file using 'java -jar cake-manager-1.0.0-SNAPSHOT.jar'
3# Once the application is compiled and jar is ready create docker build and run from docker using below commands.
    a. docker build -t cake-manager .
    b. docker run -p 8282:8282 cake-manager

Note: 2 & 3 methods should run from application context from command terminal.


and access the following URL:

API testing and see sample JSON schema.
http://localhost:8282/swagger-ui.html

API usage for Integration
GET REQUEST: will display all the cakes list available in the system
http://localhost:8282/cakes
POST REQUEST: Alternatively system will allow to new cake entry to the system using post method using same endpoint.
http://localhost:8282/cakes

Feel free to change how the project is run, but clear instructions must be given in README
You can use any IDE you like, so long as the project can build and run with Maven or Gradle.

The project loads some pre-defined data in to an in-memory database, which is acceptable for this exercise.  There is
no need to create persistent storage.


Submission
==========

Please provide your version of this project as a git repository (e.g. Github, BitBucket, etc).

Alternatively, you can submit the project as a zip or gzip. Use Google Drive or some other file sharing service to
share it with us.

Please also keep a log of the changes you make as a text file and provide this to us with your submission.

Changes made:

1. upgraded project to Spring boot 2.7 version
2. Fixed issues in the existing implementation and API is now working
3. created a new POST method to create new entries in the system.
4. created a few unit tests to cover basic functionalities. Jacoco integrated to visualize test coverage can be find. In the target folder.
5. Simple Docker file is created to build and test the API service.
6. Integrated Swagger API to visualize and try out the API endpoints.
7. Documentation updated for all public methods.
8. YAML configuration added.
9. project structure changed for easy access and readability.

