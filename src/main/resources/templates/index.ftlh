<!DOCTYPE html>
<html lang="FR">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

        <#include "css/styles.css">

        <title>Pay My Buddy - Home</title>
    </head>

    <body>
        <div class="container">
            <nav class="navbar navbar-expand-lg navbar-light">
                <div class="container-fluid">
                    <a class="navbar-brand" href="/home">
                        <button type="button" class="button-37" role="button">Pay My Buddy</button>
                    </a>
                    <div class="collapse navbar-collapse flex-row-reverse" id="navbarNavAltMarkup">
                        <div class="navbar-nav">
                            <a class="nav-link active text-primary" aria-current="page" href="#">Home</a>
                            <a class="nav-link text-dark" href="/transfer">Transfer</a>
                            <a class="nav-link text-dark" href="/profile">Profile</a>
                            <a class="nav-link text-dark" href="/contact">Contact</a>
                            <a class="nav-link text-dark" href="/logout">Log Out</a>
                        </div>
                    </div>
                </div>
            </nav>
        </div>

        <div class="container bg-light">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item active" aria-current="page">Home</li>
                </ol>
            </nav>
        </div>

        <div class="container">
            <br>
            <h2 class="d-flex justify-content-center">Welcome!</h2>
            <br>
            <#if !connectedUser.account.iban??>
                <h5 class="d-flex justify-content-center">Please do not forget to enter your iban&nbsp;<a class="text-dark" href="/profile">here</a>&nbsp;so you can start using the app.</h5>
            </#if>
        </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    </body>
</html>