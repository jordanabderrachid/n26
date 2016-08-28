package com.jordanabderrachid.n26.controllers;

import com.jordanabderrachid.n26.exceptions.AlreadyUsedTransactionIdException;
import com.jordanabderrachid.n26.exceptions.InvalidTransactionException;
import com.jordanabderrachid.n26.exceptions.UnknownTransactionIdException;
import com.jordanabderrachid.n26.exceptions.UnknownTransactionParentIdException;
import com.jordanabderrachid.n26.models.HTTPResponse;
import com.jordanabderrachid.n26.models.Transaction;
import com.jordanabderrachid.n26.models.TransactionSumResponse;
import com.jordanabderrachid.n26.services.TransactionService;
import com.jordanabderrachid.n26.validators.TransactionValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/transactionservice",
  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private TransactionValidator transactionValidator;

  @RequestMapping(value = "/transaction/{transactionId}",
    method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Transaction getTransaction(@PathVariable Long transactionId)
    throws UnknownTransactionIdException
  {
    return this.transactionService.getTransaction(transactionId);
  }

  @ExceptionHandler(UnknownTransactionIdException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public HTTPResponse handleUnknownTransactionIdException(UnknownTransactionIdException e) {
    return new HTTPResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage());
  }

  @RequestMapping(value = "/transaction/{transactionId}",
    method = RequestMethod.PUT,
    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public HTTPResponse createTransaction(@PathVariable Long transactionId, @RequestBody Transaction transaction)
    throws AlreadyUsedTransactionIdException, UnknownTransactionParentIdException, InvalidTransactionException
  {
    this.transactionValidator.validate(transaction);

    transaction.setId(transactionId);

    this.transactionService.addTransaction(transaction);

    return new HTTPResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase());
  }

  @ExceptionHandler(AlreadyUsedTransactionIdException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public HTTPResponse handleAlreadyUsedTransactionIdException(AlreadyUsedTransactionIdException e) {
    return new HTTPResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
  }

  @ExceptionHandler(UnknownTransactionParentIdException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public HTTPResponse handleUnknownTransactionParentIdException(UnknownTransactionParentIdException e) {
    return new HTTPResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
  }

  @ExceptionHandler(InvalidTransactionException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public HTTPResponse handleInvalidTransactionException(InvalidTransactionException e) {
    return new HTTPResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
  }

  @RequestMapping(value = "/types/{transactionType}",
    method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<Long> getTransactionsByType(@PathVariable String transactionType) {
    return this.transactionService.getTransactionsIdByType(transactionType);
  }

  @RequestMapping(value = "/sum/{transactionId}",
    method = RequestMethod.GET)
  public TransactionSumResponse getTransactionSum(@PathVariable Long transactionId) throws UnknownTransactionIdException {
    return new TransactionSumResponse(this.transactionService.getTransactionSum(transactionId));
  }
}
