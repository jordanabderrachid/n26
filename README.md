# N26 - Transaction API [![Build Status](https://travis-ci.org/jordanabderrachid/n26.svg?branch=master)](https://travis-ci.org/jordanabderrachid/n26)
**DISCLAIMER: This is not a [n26](https://n26.com/) official software**

The purpose of this project is to create a RESTfull API the get and create transactions. A transaction has an _id_, an _amount_, a _type_ and an optional *parent_id*. Transactions can be linked to each others using the optional *parent_id* field.

The API exposes 4 endpoints :
  1. `GET /transactionservice/transaction/{transaction_id}`
  2. `PUT /transactionservice/transaction/{transaction_id}`
  3. `GET /transactionservice/types/{type}`
  4. `GET /transactionservice/sum/{transaction_id}`

1. Get a transaction by its transaction id
2. Create a transaction
3. Get a list of transactions ids of the given type
4. Compute the sum of the amount of the parent transaction and all its child transactions.

This project is built using [Java 1.8]() and [Gradle 2.13](https://gradle.org)

#### How to run

```bash
$ git clone https://github.com/jordanabderrachid/n26.git
$ cd ./n26
$ ./gradlew bootRun
```

#### How to use

##### Creating a transaction

```bash
$ curl -X PUT -H 'Content-Type: application/json' -d '{"amount": 5000, "type": "cars"}' 'localhost:8080/transactionservice/transaction/10'
  {"code":201,"status":"Created"}
```

```bash
$ curl -X PUT -H 'Content-Type: application/json' -d '{"amount": 5000, "type": "cars"}' 'localhost:8080/transactionservice/transaction/10'
  {"code":400,"status":"Bad Request","message":"already used transaction id 10"}
```

```bash
$ curl -X PUT -H 'Content-Type: application/json' -d '{"amount": 10000, "type": "cars", "parent_id": 10}' 'localhost:8080/transactionservice/transaction/11'
  {"code":201,"status":"Created"}
```

```bash
$ curl -X PUT -H 'Content-Type: application/json' -d '{"amount": 10000, "type": "cars", "parent_id": 42}' 'localhost:8080/transactionservice/transaction/12'
  {"code":400,"status":"Bad Request","message":"unknown transaction parent id 42"}
```
##### Getting a transaction

```bash
$ curl -X GET 'localhost:8080/transactionservice/transaction/10'
  {"amount":5000.0,"type":"cars"}
```

```bash
$ curl -X GET 'localhost:8080/transactionservice/transaction/42'
 {"code":404,"status":"Not Found","message":"unknown transaction id 42"}
```

##### Getting a list of transactions ids by type

```bash
$ curl -X GET 'localhost:8080/transactionservice/types/cars'
  [10,11]
```

```bash
$ curl -X GET 'localhost:8080/transactionservice/types/foo'
  []
```

##### Getting the amount sum of a transaction and its child transactions

```bash
$ curl -X GET 'localhost:8080/transactionservice/sum/10'
  {"sum":15000.0}
```

```bash
$ curl -X GET 'localhost:8080/transactionservice/sum/11'
  {"sum":10000.0}
```
