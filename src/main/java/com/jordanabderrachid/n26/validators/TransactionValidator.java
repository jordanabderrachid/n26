package com.jordanabderrachid.n26.validators;

import com.jordanabderrachid.n26.exceptions.InvalidTransactionException;
import com.jordanabderrachid.n26.models.Transaction;

/**
 * This class is used to validate a {@link Transaction}
 *
 * @author jordanabderrachid
 */
public class TransactionValidator {

  private static final String MISSING_AMOUNT_MESSAGE = "invalid transaction, amount is required";

  private static final String MISSING_TYPE_MESSAGE = "invalid transaction, type is required";

  public TransactionValidator() {};

  /**
   * This method validate a {@link Transaction}, if the transaction is valid, no exception is thrown, otherwise a
   * {@link InvalidTransactionException} is thrown.
   *
   * @param transaction - the transaction to validate
   * @throws InvalidTransactionException
   */
  public void validate(Transaction transaction) throws InvalidTransactionException {
    if (transaction.getAmount() == null) {
      throw new InvalidTransactionException(MISSING_AMOUNT_MESSAGE);
    }

    if (transaction.getType() == null || transaction.getType().isEmpty()) {
      throw new InvalidTransactionException(MISSING_TYPE_MESSAGE);
    }
  }
}
