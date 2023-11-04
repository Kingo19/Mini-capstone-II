# Tenmo Money Transfer API Documentation

## Introduction
The Tenmo Money Transfer API provides a way to manage user accounts, transfer money, and track transaction history. This API is designed for use by a Tenmo web application and allows authenticated users to perform various operations related to their accounts and transactions.

## Base URL
The base URL for accessing the API on your local machine is http://localhost:8080.

## Authentication
The API uses authentication provided by the Tenmo web application. Users need to log in to their Tenmo accounts to access the API endpoints.

## Endpoints

### 1. Get Account Balance
- Endpoint: /balance
- Method: GET
- Description: Retrieves the current balance of the authenticated user's account.
- Request: None (Relies on authentication)
- Response:
    - Status Code: 200 OK
    - Body: A JSON object containing the current account balance.

      {
      "balance": 1000.00
      }


### 2. Get All Users
- Endpoint: /get-all-users
- Method: GET
- Description: Retrieves a list of all users.
- Request: None (Relies on authentication)
- Response:
    - Status Code: 200 OK
    - Body: A JSON array containing user information.

      [
      {
      "userId": 2,
      "username": "user2"
      },
      {
      "userId": 3,
      "username": "user3"
      }
      ]


### 3. Transfer Money
- Endpoint: /transfer
- Method: POST
- Description: Initiates a money transfer from the authenticated user's account to another user's account.
- Request:
    - Body: JSON object representing the transfer request.

      {
      "fromUserId": 1,
      "toUserId": 2,
      "amount": 50.00
      }

- Response:
    - Status Code: 200 OK
    - Body: The transfer request details.

### 4. Transaction History
- Endpoint: /history/{id}
- Method: GET
- Description: Retrieves the transaction history for the specified user.
- Request: Path parameter {id} representing the user's ID.
- Response:
    - Status Code: 200 OK
    - Body: A JSON array containing transaction history.

      [
      {
      "transferId": 1,
      "transferTypeId": 2,
      "transferStatusId": 2,
      "accountFrom": 1,
      "accountTo": 2,
      "amount": 50.00
      },
      {
      "transferId": 2,
      "transferTypeId": 2,
      "transferStatusId": 2,
      "accountFrom": 3,
      "accountTo": 1,
      "amount": 30.00
      }
      ]


### 5. Transaction Details
- Endpoint: /history/{id}/{transferId}
- Method: GET
- Description: Retrieves details of a specific transaction.
- Request: Path parameters {id} representing the user's ID and {transferId} representing the transaction ID.
- Response:
    - Status Code: 200 OK
    - Body: Details of the specified transaction.