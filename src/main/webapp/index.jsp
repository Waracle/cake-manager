<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <title>Cake Manager</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
          integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <link href="/css/narrow-jumbotron.css" rel="stylesheet">
    <style type="text/css"></style>
</head>
<body>
<div class="container">
    <header class="header clearfix">
        <h1>Cake Manager</h1>
    </header>
    <main role="main">
        <div class="jumbotron"  style="width: 30rem;">
            <h2>Add Cake</h2>
            <form action="/cakes" method="post" class="form-horizontal">
                <div class="form-group">
                    <label class="control-label col-sm-2" for="title">Title:</label>
                    <div class="col-sm-10">
                        <input name="title" id="title" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="description">Description:</label>
                    <div class="col-sm-10">
                        <input name="description" id="description" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="image">Image URL: </label>
                    <div class="col-sm-10">
                        <input name="image" id="image" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="container">
            <div class="row" style="width: 50rem;">
                <h2>Cakes currently available in the cake store:</h2>
                <c:forEach var="cake" items="${cakes}">
                    <div class="col-sm">
                        <div class="card" style="width: 20rem;">
                            <div class="card-body">
                                <h4 class="card-header">${cake.title}</h4>
                                <p class="card-text">${cake.description}</p>
                            </div>
                            <img src="${cake.image}" class="card-img-top img-fluid max-width: 100%;" style="height: 200px;" alt="Responsive image"/>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </main>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"
            integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"
            integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</div>
</body>
</html>