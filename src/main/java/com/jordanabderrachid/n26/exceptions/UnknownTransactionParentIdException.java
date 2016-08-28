package com.jordanabderrachid.n26.exceptions;

/**
 * This exception is thrown if the user try to create a transaction with an unknown parent id.
 *
 * @author jordanabderrachid
 */
public class UnknownTransactionParentIdException extends Exception {

  public UnknownTransactionParentIdException(String message) { super(message); }
}
