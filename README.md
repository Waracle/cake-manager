## Overview

### Endpoints 
Cake-Manager service have the following functionalities:
1. Add a new cake
2. Update an existing cake
3. Delete an existing cake
4. Get an existing cake
5. Get all cakes

Application uses H2 InMemory database. Data updates will be cleared when the application is restarted.

### User access
Application uses InMemoryUserDetail for application authentication and authorisation.

1.  Below user can only access get APIs (Get All Cakes and Get Cake By Id)
```
    username: 'user',
    password: 'password', 
    roles: 'USER'
```

2.  Admin user have access to get, create, update, delete APIs
```
    username: 'admin', 
    password: 'password', 
    roles: ('USER', 'ADMIN')
```

### API Documentation
[Swagger UI](http://localhost:8080/swagger-ui/index.html)

### Running the application

1. Initial static data: Application loads the static data from json file to H2 database at the application startup [cakes.json](src%2Fmain%2Fresources%2Fstatic%2Fcakes.json)

2. Run application locally in terminal

```./gradlew bootRun```

3. Run the application in a docker container locally

```docker-compose up -d```

4. Build the application using Jenkinsfile. Currently there are 3 stages setup in Jenkinsfile Build, Test and Deploy.
Docker registry details and user credentials for docker registry needs updatating in Jenkinsfile to execute the deploy stage.
