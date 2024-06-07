<!DOCTYPE html>
<html lang="FR">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

        <#include "css/styles.css">

        <title>Pay My Buddy - Transfer</title>
    </head>

    <body>
        <div class="container">
            <div class="navbar navbar-expand-lg navbar-light">
                <div class="container-fluid">
                    <a class="navbar-brand" href="/home">
                        <button type="button" class="button-37" role="button">Pay My Buddy</button>
                    </a>
                    <div class="collapse navbar-collapse flex-row-reverse" id="navbarNavAltMarkup">
                        <div class="navbar-nav">
                            <a class="nav-link text-dark" href="/home">Home</a>
                            <a class="nav-link active text-primary" aria-current="page" href="#">Transfer</a>
                            <a class="nav-link text-dark" href="/profile">Profile</a>
                            <a class="nav-link text-dark" href="/contact">Contact</a>
                            <a class="nav-link text-dark" href="/logout">Log Out</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container bg-light">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="/home">Home</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Transfer</li>
                </ol>
            </nav>
        </div>

        <div class="container" id="transfer-container">
            <div class="row">
                <#if invalidUser?? >
                    <div class="alert alert-danger" role="alert">This user email does not exist.</div>
                </#if>
                <#if existingUser?? >
                    <div class="alert alert-danger" role="alert">This user is already a connection.</div>
                </#if>
                <#if successfulConnection?? >
                    <div class="alert alert-success" role="alert">New connection successfully added!</div>
                </#if>
                <div class="d-flex justify-content-around">
                    <h5>Send Money</h5>
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addConnectionModal">Add Connection</button>
                </div>
                <div class="container" id="transfer-container-2">
                    <div class="d-flex justify-content-around" id="transfer-container-3">
                        <div class="row">
                            <select class="form-select" aria-label="Default select example">
                                <option selected>Select A Connection</option>
                                <option value="1">Friend 1</option>
                                <option value="2">Friend 2</option>
                                <option value="3">Friend 3</option>
                            </select>
                        </div>
                        <div class="row">
                            <input type="number" value="0" min="0" max="5000" step="1"/>
                        </div>
                        <div class="row">
                             <button type="button" class="btn btn-success button-37">Pay</button>
                      </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container" id="transaction-container">
            <div class="row">
                <div class="d-flex justify-content-start" id="transaction-title">
                    <h5>My Transactions</h5>
                </div>
                <table class="table table-striped text-center align-middle">
                    <thead class="transaction-table-header">
                    <tr>
                        <th scope="col">Connections</th>
                        <th scope="col">Description</th>
                        <th scope="col">Amount</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Friend 1</td>
                        <td>Tik</td>
                        <td>3e</td>
                    </tr>
                    <tr>
                        <td>Friend 2</td>
                        <td>Tax</td>
                        <td>10e</td>
                    </tr>
                    <tr>
                        <td>Friend 3</td>
                        <td>Toe</td>
                        <td>50e</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="modal fade" id="addConnectionModal" data-bs-backdrop="static" tabindex="-1" aria-labelledby="addConnectionModal" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addConnectionModal">Add A Connection</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form method="POST" action="/transfer" id="connectionForm">
                        <div class="modal-body">
                            <label for="connectionForm">Enter the email address to connect to:</label>
                            <input name="connectionEmail" type="email" class="form-control" id="connectionForm" placeholder="name@example.com">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Save changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script type="module">
        import {InputSpinner} from "./src/InputSpinner.js"

        const inputSpinnerElements = document.querySelectorAll("input[type='number']")
        for (const inputSpinnerElement of inputSpinnerElements) {
            new InputSpinner(inputSpinnerElement)
        }
    </script>
    </body>
</html>