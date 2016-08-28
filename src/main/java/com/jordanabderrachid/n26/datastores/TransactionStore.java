package com.jordanabderrachid.n26.datastores;

import com.jordanabderrachid.n26.exceptions.AlreadyUsedTransactionIdException;
import com.jordanabderrachid.n26.exceptions.UnknownTransactionIdException;
import com.jordanabderrachid.n26.exceptions.UnknownTransactionParentIdException;
import com.jordanabderrachid.n26.models.Transaction;

import java.util.List;

public interface TransactionStore {

  void addTransaction(Transaction transaction) throws AlreadyUsedTransactionIdException, UnknownTransactionParentIdException;

  Transaction getTransaction(Long transactionId) throws UnknownTransactionIdException;

  List<Long> getTransactionsIdByType(String type);

  List<Long> getChildTransactionsId(Long transactionId);
}
