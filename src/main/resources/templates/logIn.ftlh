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
                        <#if error?? >
                            <div class="alert alert-danger mt-5" role="alert" >Your credentials are incorrect.</div>
                        </#if>

                        <!--TODO: ALLOWED?? -->
                        <#if existingEmail?? >
                            <div class="alert alert-danger mt-5" role="alert">This email is already used.</div>
                        </#if>
                        <#if successfulRegistration?? >
                            <div class="alert alert-success mt-5" role="alert">Registration successful! Please log in.</div>
                        </#if>
                        <div id="login-box">
                            <form id="login-form" name="f" class="form" method="POST">

                                <div class="login-title">
                                    <button type="button" class="button-37" role="button">Pay My Buddy</button>
                                </div>

                                <!-- Email input -->
                                <div class="form-floating mb-3 form-field">
                                    <input type="email" name="username" class="form-control" id="userEmail" placeholder="Email" />
                                    <label for="userEmail">Email</label>
                                </div>

                                <!-- Password input -->
                                <div class="form-floating mb-3 form-field">
                                    <input type="password" name="password" class="form-control" id="userPassword" placeholder="Password" />
                                    <label for="userPassword">Password</label>
                                </div>

                                <!-- 2 column grid layout for inline styling -->
                                <div class="form-group">
                                    <div class="col d-flex justify-content-center">
                                        <!-- Checkbox -->
                                        <div class="checkbox">
                                            <input class="form-check-input" type="checkbox" id="remember-me" name="remember-me" />
                                            <label class="form-check-label" for="remember-me"> Remember me </label>
                                        </div>
                                    </div>
                                </div>

                                <div class="row pt-2">
                                    <div class="col text-center">
                                        <!-- Submit button -->
                                        <button type="submit" formaction="/login" form="login-form" data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-block mb-4" name="loginButton" value="login">Login</button>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col text-center">
                                        <!-- New Account button -->
                                        <button type="submit" formaction="/registration" form="login-form" data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-block mb-4" name="registrationButton" value="newAccount">Create New Account</button>
                                    </div>
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