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
                <div class="md-3 d-flex justify-content-center">
                    <#if invalidUser?? >
                        <div class="alert alert-danger" role="alert">This user email does not exist.</div>
                    </#if>
                    <#if existingUser?? >
                        <div class="alert alert-danger" role="alert">This user is already a connection.</div>
                    </#if>
                    <#if successfulConnection?? >
                        <div class="alert alert-success" role="alert">New connection successfully added!</div>
                    </#if>
                    <#if transactionError?? >
                        <div class="alert alert-danger" role="alert">The transaction could not be completed. Not enough funds available.</div>
                    </#if>
                    <#if successfulTransaction?? >
                        <div class="alert alert-success" role="alert">Transaction successful!</div>
                    </#if>
                    <#if missingConnection?? >
                        <div class="alert alert-danger" role="alert">Please select a Connection in the drop-down menu.</div>
                    </#if>
                </div>
                <div class="container mb-0">
                    <div class="d-flex justify-content-around account-balance" id="iban-container-2">
                        <div class="row">
                            <p>Your Pay My Buddy account balance:&nbsp;&nbsp;<span class="p-2 account-balance" id="pmb-balance">${user.account.accountBalance} €</span></p>
                        </div>
                    </div>
                </div>
                <div class="d-flex justify-content-around">
                    <h5>Send Money</h5>
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addConnectionModal">Add Connection</button>
                </div>
                <div class="container" id="transfer-container-2">
                    <form method="POST" action="/sendMoney" id="connectionTransactionForm">
                        <div class="d-flex justify-content-around" id="transfer-container-3">
                            <div class="row">
                                <select name="transactionConnectionEmail" id="transactionConnectionEmail" class="form-select" aria-label="Default select example">
                                    <option selected>Select A Connection</option>
                                    <#list connections_list as connection>
                                        <option name="transactionConnectionEmail" value="${connection.email}">${connection.email}</option>
                                    </#list>
                                </select>
                            </div>
                            <div class="row">
                                <input name="transactionConnectionAmount" id="transactionConnectionAmount" type="number" value="0" min="0" max="5000" step="0.01"/>
                            </div>
                            <div class="row">
                                 <button type="submit" class="btn btn-success button-37">Pay</button>
                          </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="container" id="transaction-container">
            <div class="row">
                <div class="d-flex justify-content-start" id="transaction-title">
                    <h5>My Transactions</h5>
                </div>
                <table class="table table-striped text-center align-middle transaction-table">
                    <thead class="transaction-table-header">
                        <tr>
                            <th scope="col">Connections</th>
                            <th scope="col">Date</th>
                            <th scope="col">Amount</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list transactions_list as transaction>
                            <tr>
                                <td>${transaction.transferSender.email}</td>
                                <td>${transaction.transferDate?date}</td>
                                <td>${transaction.transferAmount}&nbsp;€</td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li class="page-item">
                        <a class="page-link" href="#" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <li class="page-item"><a class="page-link" href="#">1</a></li>
                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                    <li class="page-item"><a class="page-link" href="#">4</a></li>
                    <li class="page-item"><a class="page-link" href="#">5</a></li>
                    <li class="page-item">
                        <a class="page-link" href="#" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>


        <div class="modal fade" id="addConnectionModal" data-bs-backdrop="static" tabindex="-1" aria-labelledby="addConnectionModal" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addConnectionModal">Add A Connection</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form method="POST" action="/addConnection" id="connectionForm">
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
    </body>
</html>