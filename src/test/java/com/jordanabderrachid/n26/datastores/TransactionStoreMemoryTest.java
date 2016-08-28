package com.jordanabderrachid.n26.datastores;

import com.jordanabderrachid.n26.exceptions.AlreadyUsedTransactionIdException;
import com.jordanabderrachid.n26.exceptions.UnknownTransactionIdException;
import com.jordanabderrachid.n26.exceptions.UnknownTransactionParentIdException;
import com.jordanabderrachid.n26.models.Transaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TransactionStoreMemoryTest {

  @Test(expected = AlreadyUsedTransactionIdException.class)
  public void testAddTransactionWithAlreadyUsedId() throws AlreadyUsedTransactionIdException, UnknownTransactionParentIdException {
    TransactionStore transactionStore = new TransactionStoreMemory();

    Transaction transaction = new Transaction();
    transaction.setId(new Long(1));
    transaction.setType("foo");
    transaction.setAmout(new Double(1));

    transactionStore.addTransaction(transaction);
    transactionStore.addTransaction(transaction);
  }

  @Test(expected = UnknownTransactionParentIdException.class)
  public void testAddTransactionWithUnknownTransactionParentId()
    throws AlreadyUsedTransactionIdException, UnknownTransactionParentIdException
  {
    TransactionStore transactionStore = new TransactionStoreMemory();

    Transaction transaction = new Transaction();
    transaction.setId(new Long(1));
    transaction.setAmout(new Double(2));
    transaction.setType("foo");
    transaction.setParentId(new Long(2));

    transactionStore.addTransaction(transaction);
  }

  @Test(expected = UnknownTransactionIdException.class)
  public void testGetTransactionWithUnknownTransactionId() throws UnknownTransactionIdException {
    TransactionStore transactionStore = new TransactionStoreMemory();

    transactionStore.getTransaction(new Long(1));
  }

  @Test
  public void testGetTransaction()
    throws UnknownTransactionIdException, AlreadyUsedTransactionIdException, UnknownTransactionParentIdException
  {
    TransactionStore transactionStore = new TransactionStoreMemory();

    Transaction transaction = new Transaction();
    transaction.setId(new Long(1));
    transaction.setAmout(new Double(2));
    transaction.setType("foo");

    transactionStore.addTransaction(transaction);

    assertThat(transactionStore.getTransaction(transaction.getId()).equals(transaction)).isTrue();
  }

  @Test
  public void testGetTransactionsIdByType()
    throws AlreadyUsedTransactionIdException, UnknownTransactionParentIdException
  {
    TransactionStore transactionStore = new TransactionStoreMemory();

    Transaction transactionFoo1 = new Transaction();
    transactionFoo1.setId(new Long(1));
    transactionFoo1.setType("foo");
    transactionStore.addTransaction(transactionFoo1);

    Transaction transactionFoo2 = new Transaction();
    transactionFoo2.setId(new Long(2));
    transactionFoo2.setType("foo");
    transactionStore.addTransaction(transactionFoo2);

    Transaction transactionBar = new Transaction();
    transactionBar.setId(new Long(3));
    transactionBar.setType("bar");
    transactionStore.addTransaction(transactionBar);

    List<Long> transactionsIdFooBar = transactionStore.getTransactionsIdByType("foobar");
    assertThat(transactionsIdFooBar.isEmpty()).isTrue();

    List<Long> transactionsIdFoo = transactionStore.getTransactionsIdByType(transactionFoo1.getType());
    assertThat(transactionsIdFoo.isEmpty()).isFalse();
    assertThat(transactionsIdFoo.size() == 2).isTrue();
    assertThat(transactionsIdFoo.contains(transactionFoo1.getId())).isTrue();
    assertThat(transactionsIdFoo.contains(transactionFoo2.getId())).isTrue();

    List<Long> transactionsIdBar = transactionStore.getTransactionsIdByType(transactionBar.getType());
    assertThat(transactionsIdBar.isEmpty()).isFalse();
    assertThat(transactionsIdBar.size() == 1).isTrue();
    assertThat(transactionsIdBar.contains(transactionBar.getId())).isTrue();
  }

  @Test
  public void testGetChildTransactionWithNoChild() {
    TransactionStore transactionStore = new TransactionStoreMemory();

    assertThat(transactionStore.getChildTransactionsId(new Long(1)).isEmpty()).isTrue();
  }

  @Test
  public void testGetChildTransactionWithChild()
    throws UnknownTransactionParentIdException, AlreadyUsedTransactionIdException
  {
    TransactionStore transactionStore = new TransactionStoreMemory();

    Transaction transactionParent = new Transaction();
    transactionParent.setId(new Long(1));
    transactionParent.setType("foo");

    Transaction transactionChild1 = new Transaction();
    transactionChild1.setId(new Long(2));
    transactionChild1.setType("foo");
    transactionChild1.setParentId(transactionParent.getId());

    Transaction transactionChild2 = new Transaction();
    transactionChild2.setId(new Long(3));
    transactionChild2.setType("foo");
    transactionChild2.setParentId(transactionParent.getId());

    transactionStore.addTransaction(transactionParent);
    transactionStore.addTransaction(transactionChild1);
    transactionStore.addTransaction(transactionChild2);

    List<Long> childTransactionsId = transactionStore.getChildTransactionsId(transactionParent.getId());
    assertThat(childTransactionsId.isEmpty()).isFalse();
    assertThat(childTransactionsId.size() == 2).isTrue();
    assertThat(childTransactionsId.contains(transactionChild1.getId())).isTrue();
    assertThat(childTransactionsId.contains(transactionChild2.getId())).isTrue();
  }
}
