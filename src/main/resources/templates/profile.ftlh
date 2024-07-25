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
                <div class="d-flex justify-content-around">
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
                    <#if ibanSuccess?? >
                        <div class="alert alert-success" role="alert">Iban successfully register/updated.</div>
                    </#if>
                    <#if ibanError?? >
                        <div class="alert alert-danger" role="alert">Error registering/updating iban.</div>
                    </#if>
                    <#if nullIbanError?? >
                        <div class="alert alert-danger" role="alert">Iban cannot be blank.</div>
                    </#if>
                    <#if nullIbanTransferError?? >
                        <div class="alert alert-danger" role="alert">Register an iban before doing a transfer.</div>
                    </#if>
                </div>
                <div class="d-flex justify-content-around">
                    <h4>Bank <> Pay My Buddy Transfers</h4>
                </div>
                <#if user.account.iban?? && user.account.iban?has_content>
                    <div class="container mb-0" id="iban-container">
                        <div class="d-flex justify-content-around account-balance" id="iban-container-2">
                            <div class="row">
                                <p>Your Bank account balance:&nbsp;&nbsp;<span class="bg-white p-2 account-balance">${user.account.bankBalance} €</span></p>
                            </div>
                            <div class="row">
                                <p>Your Pay My Buddy account balance:&nbsp;&nbsp;<span class="bg-white p-2 account-balance">${user.account.accountBalance} €</span></p>
                            </div>
                        </div>
                    </div>
                <#else>
                    <div class="d-flex justify-content-around">
                        <div class="alert alert-warning" id="iban-alert" role="alert">
                            <h6>Register your iban below to have access to your account balance:</h6>
                        </div>
                    </div>
                </#if>
                <div class="container" id="iban-container">
                    <form method="POST" action="/ibanRegistration" id="ibanRegistrationForm">
                        <div class="d-flex justify-content-around" id="iban-container-2">
                            <div class="row" id="iban-input-field">
                                <input type="text" name="iban" class="form-control" id="iban" placeholder="Your iban here" value="${user.account.iban!}" />
                            </div>
                            <div class="row">
                                <button type="submit" class="btn btn-success button-37">Register/Update my iban</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="container" id="transfer-container-2">
                    <form method="POST" action="/bankAccountTransfer" id="bankTransferForm">
                        <div class="d-flex justify-content-around" id="transfer-container-3">
                            <div class="row">
                                <select name="bankTransferAction" id="bankTransferAction" class="form-select" aria-label="Default select example">
                                    <option selected>I want to...</option>
                                    <option value="bankToAccount">Transfer money from my Bank to Pay My Buddy</option>
                                    <option value="accountToBank">Transfer money from Pay My Buddy to my Bank</option>
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