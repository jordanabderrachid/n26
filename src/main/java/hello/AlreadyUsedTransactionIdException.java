package hello;

public class AlreadyUsedTransactionIdException extends Exception {

  public AlreadyUsedTransactionIdException(String message) { super(message); }
}
