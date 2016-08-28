package com.jordanabderrachid.n26.exceptions;

/**
 * This exception is thrown if the id a of transaction is already set in the transaction store.
 *
 * @author jordanabderrachid
 */
public class AlreadyUsedTransactionIdException extends Exception {

  public AlreadyUsedTransactionIdException(String message) { super(message); }
}
