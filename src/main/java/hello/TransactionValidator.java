package hello;

public class TransactionValidator {

  private static final String MISSING_AMOUNT_MESSAGE = "invalid transaction, amount is required";

  private static final String MISSING_TYPE_MESSAGE = "invalid transaction, type is required";

  public TransactionValidator() {};

  public void validate(Transaction transaction) throws InvalidTransactionException {
    if (transaction.getAmount() == null) {
      throw new InvalidTransactionException(MISSING_AMOUNT_MESSAGE);
    }

    if (transaction.getType() == null || transaction.getType().isEmpty()) {
      throw new InvalidTransactionException(MISSING_TYPE_MESSAGE);
    }
  }
}
