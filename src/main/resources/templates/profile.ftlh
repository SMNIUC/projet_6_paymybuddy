<!DOCTYPE html>
<html lang="FR">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

        <#include "css/styles.css">

        <title>Pay My Buddy - Profile</title>
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
                            <a class="nav-link active text-primary" aria-current="page" href="#">Profile</a>
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
                    <li class="breadcrumb-item"><a href="/home">Home</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Profile</li>
                </ol>
            </nav>
        </div>

        <div class="container" id="transfer-container">
            <div class="row">
                <#if selectAnAction?? >
                    <div class="alert alert-danger" role="alert">Please select an action in the drop-down menu 'I want to...'.</div>
                </#if>
                <#if balanceTooLow?? >
                    <div class="alert alert-danger" role="alert">You do not have enough funds.</div>
                </#if>
                <#if bankRefusal?? >
                    <div class="alert alert-danger" role="alert">The bank refused the transfer.</div>
                </#if>
                <#if successfulTransfer?? >
                    <div class="alert alert-success" role="alert">Transfer successful!</div>
                </#if>
                <div class="d-flex justify-content-around">
                    <h5>Bank <> Account Transfers</h5>
                </div>
                <div class="container" id="transfer-container-2">
                    <form method="POST" action="/profile" id="bankTransferForm">
                        <div class="d-flex justify-content-around" id="transfer-container-3">
                            <div class="row">
                                <select name="bankTransferAction" id="bankTransferAction" class="form-select" aria-label="Default select example">
                                    <option selected>I want to...</option>
                                    <option value="bankToAccount">Transfer money from my bank to my account</option>
                                    <option value="accountToBank">Transfer money from my account to my bank</option>
                                </select>
                            </div>
                            <div class="row">
                                <input name="bankTransferAmount" id="bankTransferAmount" type="number" data-suffix="€" value="0" min="0" max="5000" step="0.01"/>
                            </div>
                            <div class="row">
                                <button type="submit" class="btn btn-success button-37">Transfer</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    </body>
</html>