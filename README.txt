Jennifer Edgar's notes:

To run
-----------
I just left it using java 1.8
It is now using springboot and angular

Open terminal and move to the cake-manager directory - where the pom.xml is - mvn spring-boot:run
open another terminal window and move to the cake-manager/cient directory - ng serve

The url - http://localhost:4200/cakes - to download and add cakes,   http://localhost:4200/ - to display the saved cakes


List of tasks I have completed
------------------------------
replace servlet functionality with springboot  restful api - created dependencies within the pom, create main method application runner.
remove the webapp directory
create appropriate packages
create cake rest controller
rename CakeEntity to Cake
specified the port number within application.properties
fix hibernate issue -created dependancy for hibernate.core
change employeeId within cakeEntity to id.
split out the hibernate functionality from the servlet.
removed servlet
change the table name from employee to cake
changing the column names to be more appropriate to the cake and json names.
some of the images cannot be found - so just leaving this as a link rather than displaying the image.
updated the version of hibernate
created angular 10 client with add, list and get functionality.
introduced try with resources
inserted css
imported bootstrap css
Created CakeServiceImpl and CakeService to split out the logic from the controller
created display cakes angular component.
added console logging
replaced system.out.println with log output
created deletion for testing purposes
removed duplicate unique constraint delclarations within entity
wrote some unit tests and integration tests








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

Alternatively, you can submit the project as a zip or gzip. Use Google Drive or some other file sharing service to
share it with us.

Please also keep a log of the changes you make as a text file and provide this to us with your submission.

Good luck!
