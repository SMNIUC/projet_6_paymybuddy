<!DOCTYPE html>
<html lang="FR">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

        <#include "css/styles.css">

        <title>Pay My Buddy - Log In</title>
    </head>
        <body>
        <div id="login">
            <div class="container">
                <div id="login-row" class="row justify-content-center align-items-center">
                    <div id="login-column" class="col-md-3">
                        <div id="login-box" class="col-md-12">
                            <form id="login-form" name="f" class="form" action="/login" method="POST">
                                <div class="login-title">
                                    <button type="button" class="button-37" role="button">Pay My Buddy</button>
                                </div>

                                <!-- Email input -->
                                <div class="form-floating mb-3 form-field">
                                    <input type="email" name="username" class="form-control" id="floatingInput" placeholder="Email" />
                                    <label for="floatingInput">Email</label>
                                </div>

                                <!-- Password input -->
                                <div class="form-floating mb-3 form-field">
                                    <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Password" />
                                    <label for="floatingPassword">Password</label>
                                </div>

                                <!-- 2 column grid layout for inline styling -->
                                <div class="form-group">
                                    <div class="col d-flex justify-content-center">
                                        <!-- Checkbox -->
                                        <div class="checkbox">
                                            <input class="form-check-input" type="checkbox" value="" id="remember-me" />
                                            <label class="form-check-label" for="remember-me"> Remember me </label>
                                        </div>
                                    </div>
                                </div>

                                <!-- Submit button -->
                                <div class="login-button">
                                    <button type="submit" data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-block mb-4">Login</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    </body>
</html>