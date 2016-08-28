package com.jordanabderrachid.n26.exceptions;

/**
 * This exception is thrown if the user try to get a transaction with an unknown id.
 *
 * @author jordanabderrachid
 */
public class UnknownTransactionIdException extends Exception {

  public UnknownTransactionIdException(String message) { super(message); }
}
