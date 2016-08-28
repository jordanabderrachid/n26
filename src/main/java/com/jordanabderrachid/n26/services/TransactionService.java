package com.jordanabderrachid.n26.services;

import com.jordanabderrachid.n26.datastores.TransactionStore;
import com.jordanabderrachid.n26.exceptions.AlreadyUsedTransactionIdException;
import com.jordanabderrachid.n26.exceptions.UnknownTransactionIdException;
import com.jordanabderrachid.n26.exceptions.UnknownTransactionParentIdException;
import com.jordanabderrachid.n26.models.Transaction;

import java.util.List;

/**
 * This service is used to interract with the transaction store.
 *
 * @author jordanabderrachid
 */
public class TransactionService {
  private TransactionStore transactionStore;

  public TransactionService(TransactionStore transactionStore) { this.transactionStore = transactionStore; }

  /**
   * Add a {@link Transaction} to the transaction store.
   *
   * @param transaction - the transaction to add
   * @throws AlreadyUsedTransactionIdException
   * @throws UnknownTransactionParentIdException
   */
  public void addTransaction(Transaction transaction)
    throws AlreadyUsedTransactionIdException, UnknownTransactionParentIdException {
    this.transactionStore.addTransaction(transaction);
  }

  /**
   * Get a {@link Transaction} by its id.
   *
   * @param transactionId - the id of the transaction
   * @return the transaction
   * @throws UnknownTransactionIdException
   */
  public Transaction getTransaction(Long transactionId)
    throws UnknownTransactionIdException {
    return this.transactionStore.getTransaction(transactionId);
  }

  /**
   * Get the list of transactions id of a given type.
   *
   * @param type - the transaction type
   * @return the list of transactions id of the given type
   */
  public List<Long> getTransactionsIdByType(String type) { return this.transactionStore.getTransactionsIdByType(type); }

  /**
   * Get the sum of a transaction and all the child transactions. The time complexity of this method is O(n) where n
   * is the number of child transactions.
   *
   * @param transactionId - the id of the transaction
   * @return - the sum
   * @throws UnknownTransactionIdException
   */
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
