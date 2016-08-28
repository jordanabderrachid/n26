package hello;

import java.util.List;

public interface TransactionStore {

  void addTransaction(Transaction transaction) throws AlreadyUsedTransactionIdException, UnknownTransactionParentIdException;

  Transaction getTransaction(Long transactionId) throws UnknownTransactionIdException;

  List<Long> getTransactionsIdByType(String type);

  List<Long> getChildTransactionsId(Long transactionId);
}
