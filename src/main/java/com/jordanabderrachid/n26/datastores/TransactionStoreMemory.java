package com.jordanabderrachid.n26.datastores;

import com.jordanabderrachid.n26.exceptions.AlreadyUsedTransactionIdException;
import com.jordanabderrachid.n26.exceptions.UnknownTransactionIdException;
import com.jordanabderrachid.n26.exceptions.UnknownTransactionParentIdException;
import com.jordanabderrachid.n26.models.Transaction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementation of the transaction store in the memory.
 *
 * @author jordanabderrachid
 */
public class TransactionStoreMemory implements TransactionStore {

  /**
   * map the transaction id to the transaction
   */
  private Map<Long, Transaction> transactions;

  /**
   * map a transaction type to a list of transaction ids
   */
  private Map<String, List<Long>> types;

  /**
   * map the id of a parent transaction to the list of child transaction ids
   */
  private Map<Long, List<Long>> transactionRelations;

  private ReentrantLock lock;

  public TransactionStoreMemory() {
    this.transactions = new HashMap<>();
    this.types = new HashMap<>();
    this.transactionRelations = new HashMap<>();
    this.lock = new ReentrantLock();
  }

  /**
   * Add a {@link Transaction} to the store. If the transaction id is already used, a {@link AlreadyUsedTransactionIdException}
   * is thrown. If the parent id of the transaction is unknown, a {@link UnknownTransactionParentIdException} is
   * thrown.
   *
   * A lock is used to handle concurrency access to the data.
   *
   * @param transaction - the transaction to add
   * @throws AlreadyUsedTransactionIdException
   * @throws UnknownTransactionParentIdException
   */
  public void addTransaction(Transaction transaction)
    throws AlreadyUsedTransactionIdException, UnknownTransactionParentIdException {
    this.lock.lock();
    try {
      if (this.transactions.containsKey(transaction.getId())) {
        throw new AlreadyUsedTransactionIdException("already used transaction id " + transaction.getId());
      }

      if (transaction.hasParentTransaction() && !this.transactions.containsKey(transaction.getParentId())) {
        throw new UnknownTransactionParentIdException("unknown transaction parent id " + transaction.getParentId());
      }

      this.transactions.put(transaction.getId(), transaction);

      if (this.types.containsKey(transaction.getType())) {
        this.types.get(transaction.getType()).add(transaction.getId());
      } else {
        List<Long> list = new LinkedList<>();
        list.add(transaction.getId());
        this.types.put(transaction.getType(), list);
      }

      if (transaction.hasParentTransaction()) {
        if (this.transactionRelations.containsKey(transaction.getParentId())) {
          this.transactionRelations.get(transaction.getParentId()).add(transaction.getId());
        } else {
          List<Long> list = new LinkedList<>();
          list.add(transaction.getId());
          this.transactionRelations.put(transaction.getParentId(), list);
        }
      }
    } finally {
      this.lock.unlock();
    }
  }

  /**
   * Get a {@link Transaction} by its transaction id. If the id is unknown, a {@link UnknownTransactionIdException} is
   * thrown.
   *
   * A lock is used to handle concurrency access to the data.
   *
   * @param transactionId - the id of the transaction
   * @return the transaction
   * @throws UnknownTransactionIdException
   */
  public Transaction getTransaction(Long transactionId)
    throws UnknownTransactionIdException {
    this.lock.lock();
    try {
      if (!this.transactions.containsKey(transactionId)) {
        throw new UnknownTransactionIdException("unknown transaction id " + transactionId);
      }

      return this.transactions.get(transactionId);
    } finally {
      this.lock.unlock();
    }
  }

  /**
   * Get the list of transaction id of the given type.
   *
   * A lock is used to handle concurrency access to the data.
   *
   * @param type - the transaction type
   * @return the list of the transaction ids
   */
  public List<Long> getTransactionsIdByType(String type) {
    this.lock.lock();
    try {
      if (!this.types.containsKey(type)) {
        return new LinkedList<>();
      }

      return new LinkedList<>(this.types.get(type));
    } finally {
      this.lock.unlock();
    }
  }

  /**
   * Get the list of child transaction id of the given parent transaction id.
   *
   * A lock is used to handle concurrency access to the data.
   *
   * @param transactionId - the parent transaction id
   * @return the list of child transactions ids
   */
  public List<Long> getChildTransactionsId(Long transactionId) {
    this.lock.lock();
    try {
      if (!this.transactionRelations.containsKey(transactionId)) {
        return new LinkedList<>();
      }

      return new LinkedList<>(this.transactionRelations.get(transactionId));
    } finally {
      this.lock.unlock();
    }
  }
}
