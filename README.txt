
Cake Manager Micro Service (fictitious)
=======================================

A summer intern started on this project but never managed to get it finished.


Requirements:

* By accessing the root of the server (/) it should be possible to list the cakes currently in the system.  This
 must be presented in an acceptable format for a human to read.

* It must be possible for a human to add a new cake to the server.

* By accessing an alternative endpoint (/cakes) with an appropriate client it must be possible to download a list of
 the cakes currently in the system as JSON data.

* Accessing the /cakes endpoint with a web browser must show the human presentation of the list of cakes.

* The /cakes endpoint must also allow new cakes to be created.


The developer assured us that some of the above is complete, but at the moment accessing the /cakes endpoint
 returns a 404, so getting this working should be the first priority.

There may be other bugs and mistakes, feel free to fix anything you find. Likewise, feel free to re-organise,
 refactor or re-write the project anyway you see fit.



Project Info
============

The project uses Maven and Servlet 3.0.

To run a server locally execute the following command:

`mvn jetty:run`

and access the following URL:

`http://localhost:8282/`


You can use any IDE you like, so long as the project can build and run with Maven.

The project loads some pre-defined data in to an in-memory database, which is acceptable for this exercise.  There is
 no need to create persistent storage.


Submission
==========


Please provide your version of this project as a zip or gzip.   Use Google Drive or some other file sharing service to
share it with us.

Alternatively, you can submit the location of a git repository (e.g. Github, BitBucket, etc).

Please also keep a log of the changes you make as a text file and provide this to us with your submission.

Good luck!
