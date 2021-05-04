# SOLUTION NOTES

## CI URL

https://app.circleci.com/pipelines/github/warnettconsultingltd/wcl-cake-manager

# DEVELOPMENT NOTES

## ISSUE 1 - Project Setup and Creation

The decision was made to uplift the codebase from the old Servlet / JSP style into a more
modern Spring Boot microservice style application.  The nature of the problem suggested a clear delineation 
between client and server.

As both client and server are resident within the same project, there is an issue that only
one SpringBootApplication can be present in a project as an entry point.

The solution was thus to make the client and server controllers with specific, distinct Spring profiles.
The produced jar file is thus invoked with wither client or server Spring profile and launches
the correct instance.

### TECH STACK

- Java 16
- Spring Boot
- Thymeleaf for client
- H2 local database

### IMPROVEMENTS

- Split project into separate client / server projects.  This would enable benefits such as
scalability, load balancing etc as found with microservices.
  
## ISSUE 2 - ADD CAKE ENTITY

First cut of an entity representing a Cake.

## ISSUE 3 - SERVICE CLASS FOR CAKES

Introduced a Service class, CakeService, on the server side to service the defined server
endpoint, "/" to return a human readable version of Cakes, "/cakes" to return the cake list in
JSON format.

Dummy data was returned from the service class at this point in time to satisfy tests - database
interaction to be determinCHANGESed at a later stage.

## ISSUE 4 - RETRIEVE EXISTING CAKE DATA

A gist URL was used in the original solution to retrieve seed Cake data.

A separate entity was introduced to model the seed data; the data contains title, desc and image
values which doesn't necessarily lend itself well to a natural primary key.

The JSON was converted to a list of SeedDataCake objects and is returned from the utility class.

## ISSUE 5 - DUPLICATES IN SEED DATA

The JSON seed data was observed to return duplicates; the assumption being made here is that the
intention was for one of each cake type to be contained - multiple cakes would be an odd way to model
number of cakes available, after all.

The solution is to return a Set rather than a List to strip out the duplicate entities; the
Java 16 records should handle equals correctly in this instance.

## ISSUE 6 - DEFINE IN MEMORY H2 DATABASE

A DDL file was created, data.sql, to create a Cakes table.

CREATE TABLE Cakes (
  id uuid default random_uuid() PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL,
  imageurl VARCHAR(250) NOT NULL
);

As the seed data contains no primary key value - there can be different lemon cheesecakes and Strings 
as primary keys are generally meh to say the least, the database will assign a random
UUID to be used as primary key.

The application-apiserver.properties file was amended to add in database properties.

### IMPROVEMENTS

data.sql is invoked by each Spring profile, so in this fake example, the client will also
have a table established - a table never used.  This is a consequence of client / server
being defined in the same project.  This can be resolved by implementing the client and
server as separate projects, as mentioned before.

## ISSUE 7 - POPULATING DB WITH SEED DATA

Populating the seed data needs to be a one-off action; this was achieved by creating a 
SeedDataService, autowired with the previously created SeedDataRetriever and a newly created
CakeRepository instance.

The CakeRepository is a JPA Repository, for simplicity.

The service uses a Java 8 stream to convert and persist the SeedDataCake entities.  Why convert?
After all, surely it would be simpler to have a single class rather than a class for seed data, a
class representing a cake and a class for the JPA entity?  For me, I prefer POJOs to be as free
as annotations as possible; after all, @Id only really has context within a persistence context.
Yes, it creates slightly more work in converting - and something like MapStruct could be potentially
used for that - but it means that areas of the code become cleaner to read.  

If the project was split into two projects - client and server - then the project structure would be
better served by using a maven multi-module project, of which persistence would be one.  That would
enable the JPA annotations, dependencies to be entirely self contained within a persistence module 
as opposed to leaking to other modules.

A test was written to trigger the starting of the app server; the contents of the in memory db
are then evaluated.

## ISSUE 8 - ADDING CIRCLE CI

I use Circle CI - used to use Travis but ran out of free credits....SS

### IMPROVEMENTS

- Include quality metrics - coverage stats could be pushed to something like codecov.
- Include code analysis reports

## ISSUE 9 - CREATE INTIIAL UI

Built a simple Thymeleaf UI, with boxes for containing a list of Cakes and the cake list JSON.

Added a simple service on the uiclient side to retrieve data to populate the Thymeleaf model
correctly; for this git push, uses dummy data.

Added tests of the Thymeleaf page with regards to model population.