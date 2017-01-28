# Notes #

A log of changes is requested, and although this is easily taken from git, i'll
keep track of the highlights and any pertinant thoughts as i go along, here.

# Requirements #

The / and /cakes endpoints are described identically. I could redirect / to /cakes
or have the servlet respond for both.

RESTful CRUD service with JSON & HTML content types:
* GET / -> redirect to /cakes (or just mount the servlet at both / and /cakes?)
* GET /cakes -> HTML list of cakes, with form to add cakes
* GET /cakes -> JSON list of cakes
