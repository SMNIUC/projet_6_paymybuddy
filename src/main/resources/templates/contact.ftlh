<!DOCTYPE html>
<html lang="FR">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

        <#include "css/styles.css">

        <title>Pay My Buddy - Contact</title>
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
                            <a class="nav-link text-dark" href="/home">Home</a>
                            <a class="nav-link text-dark" href="/transfer">Transfer</a>
                            <a class="nav-link text-dark" href="/profile">Profile</a>
                            <a class="nav-link active text-primary" aria-current="page" href="#">Contact</a>
                            <a class="nav-link text-dark" href="/logout">Log Out</a>
                        </div>
                    </div>
                </div>
            </nav>
        </div>

        <div class="container bg-light">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="/home">Home</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Contact</li>
                </ol>
            </nav>
        </div>

        <div class="container">
            <div class="container" id="transaction-container">
                <div class="row">
                    <div class="d-flex justify-content-start" id="transaction-title">
                        <h5>My Information</h5>
                    </div>
                    <form>
                        <div class="mb-3">
                            <label for="accountId" class="form-label">Account ID</label>
                            <input type="email" class="form-control" id="accountId" aria-describedby="emailHelp" disabled="disabled" value="${connectedUser.account.accountId}">
                        </div>
                        <div class="mb-3">
                            <label for="userEmail" class="form-label">Email address</label>
                            <input type="email" class="form-control" id="userEmail" aria-describedby="emailHelp" value="${connectedUser.email}">
                            <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
                        </div>
                        <button type="submit" class="btn btn-primary">Update your email</button>
                    </form>
                </div>
            </div>
        </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    </body>
</html>