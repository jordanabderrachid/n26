package hello;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionStoreMemory implements TransactionStore {

  private Map<Long, Transaction> transactions;

  private Map<String, List<Long>> types;

  private Map<Long, List<Long>> transactionRelations;

  private ReentrantLock lock;

  public TransactionStoreMemory() {
    this.transactions = new HashMap<>();
    this.types = new HashMap<>();
    this.transactionRelations = new HashMap<>();
    this.lock = new ReentrantLock();
  }

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

