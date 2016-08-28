package com.jordanabderrachid.n26.exceptions;

/**
 * This exception is thrown if the transaction sent by the user is invalid.
 *
 * @author jordanabderrachid
 */
public class InvalidTransactionException extends Exception {

  public InvalidTransactionException(String message) {
    super(message);
  }
}
