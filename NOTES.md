# Notes #

A log of changes is requested, and although this is easily taken from git, i'll
keep track of the highlights and any pertinant thoughts as i go along, here.

# Requirements #

The / and /cakes endpoints are described identically. I could redirect / to /cakes
or have the servlet respond for both.

RESTful CRUD service with JSON & HTML content types:
* GET / -> redirect to /cakes (or just mount the servlet at both / and /cakes?)
* GET /cakes [Accepts: text/html] -> HTML list of cakes, with form to add cakes
* GET /cakes [Accepts: application/json] -> JSON list of cakes

My submission form will use ajax, but could have used old-skool post-redirect-get.

Perhaps even a combination of the two - i.e. default to PRG pattern but if javascript
runs, then hook the form .onSubmit() then .preventDefault() and handle submission
by ajax. This would also allow me to eagerly update the UI client side with the
new entry, even before the server has received it. This would make the app feel
faster to the user.

# Code review #

Notes from reviewing the existing solution:

    CakeServlet.java
     init()
        * This code could be organised in a way that's easier to test and maintain
          e.g. init() has too many responsibilities like downloading json and
          initialising data stores. These are not useful responsibilities for a
          Servlet to own. Ideally, the servlet would own the thin sliver of 
          functionality to interface with the machinery that is the web & http
        * Consider adopting a logging library and refactoring out println() calls
        * How trustworthy is this json data source, could it be compromised
          (or could your call be intercepted) to return infinite data and consume
          your entire JVM heap? Potential DOS attack vector. Potenial for poisoning.
        * Good use of try-with-resources, *thumbs up emoji*
        * The logging strategy used here isn't going to help identify many common
          production type issues. E.g. say the app's connection on the DB is
          blocked by some DBA process (maybe a DB backup is running), the logging
          will fail to show enough information about what's going on
        * This class, in fact this project, with it's current scope and stated
          function / non-functional reqs, would benefit from something like
          spring boot to eliminate low value "busy work" code that is present
       doGet()
        * Consider having basic metrics on services - even just the absolute minimum
          like count of requests made, count of requests failed and count of requests
          succeeded. You can get fancy later on when there's justification for it
          but the number of times i've been glad of such basic instrumentation
          in prod...
        * Manual serialisation is error prone not to mention low value, jackson
          is already a project dependency, consider using that instead
        * The response will not be valid json - missing "," between list items
        
    CakeEntity.java:
        * Use of depricated @Entity, consider replacing with @DynamicUpdate
        * Unconventional db <-> class field mappings, looks like copy-pasta
         * Table called "Employee" for class "CakeEntity"
         * Column "EMAIL" used for field "title"
         * etc. etc.
        * Surrogate key field is named "employeeId"
        * Don't use both @UniqueConstraint and @Column(unique=true)
        * It's ok to depend on hibernate provided equals() and hashCode() here *thumbs up emoji*
        
    pom.xml:
        * URL field isn't accurate
        * Old version specified for junit, typo?
        * No version specified for jetty plugin
        * The commentary is redundant - It's usually better to answering the
          question "Why?" in comments like this since the reader can already see
          the "What"
        * Consider extracting the literal version numbers to property fields to
          make life better for future hackers on this code

    index.jsp:
        * Not used, remove?
        * Use a doc type, try to avoid quirks mode rendering where possible
    
    Web.xml:
        * 2.3 must be a typo here since 3.0 is specified in the reqs and 3.1 is
          adopted in the POM already

# Fix The Existing Code #

I used AngularJS style commit messages (can be handy for tools like git bisect).

https://github.com/CraigJPerry/cake-manager/commits/interview-assessment

