package hello;

import java.util.List;

public class TransactionService {
  private TransactionStore transactionStore;

  public TransactionService(TransactionStore transactionStore) { this.transactionStore = transactionStore; }

  public void addTransaction(Transaction transaction)
    throws AlreadyUsedTransactionIdException, UnknownTransactionParentIdException {
    this.transactionStore.addTransaction(transaction);
  }

  public Transaction getTransaction(Long transactionId)
    throws UnknownTransactionIdException {
    return this.transactionStore.getTransaction(transactionId);
  }

  public List<Long> getTransactionsIdByType(String type) { return this.transactionStore.getTransactionsIdByType(type); }

  public Double getTransactionSum(Long transactionId) throws UnknownTransactionIdException {
    Transaction transaction = this.getTransaction(transactionId);

    Double sum = transaction.getAmount();

    for (Long childTransactionId : this.transactionStore.getChildTransactionsId(transactionId)) {
      try {
        Transaction childTransaction = this.transactionStore.getTransaction(childTransactionId);
        sum += childTransaction.getAmount();
      } catch (UnknownTransactionIdException e) {
        // TODO log error
      }
    }

    return sum;
  }
}
