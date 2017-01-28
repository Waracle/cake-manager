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
