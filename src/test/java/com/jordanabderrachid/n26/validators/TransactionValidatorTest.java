package com.jordanabderrachid.n26.validators;

import com.jordanabderrachid.n26.exceptions.InvalidTransactionException;
import com.jordanabderrachid.n26.models.Transaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TransactionValidatorTest {

  @Autowired
  private TransactionValidator transactionValidator;

  @Test(expected = InvalidTransactionException.class)
  public void testValidateNoAmout() throws InvalidTransactionException {
    Transaction transactionNoAmount = new Transaction();
    transactionNoAmount.setId(new Long(1));
    transactionNoAmount.setType("type");
    transactionNoAmount.setParentId(new Long(2));

    transactionValidator.validate(transactionNoAmount);
  }

  @Test(expected = InvalidTransactionException.class)
  public void testValidateNoType() throws InvalidTransactionException {
    Transaction transactionNoType = new Transaction();
    transactionNoType.setId(new Long(1));
    transactionNoType.setAmout(new Double(1));
    transactionNoType.setParentId(new Long(2));

    transactionValidator.validate(transactionNoType);
  }

  @Test(expected = InvalidTransactionException.class)
  public void testValidateEmptyType() throws InvalidTransactionException {
    Transaction transactionEmptyType = new Transaction();
    transactionEmptyType.setId(new Long(1));
    transactionEmptyType.setAmout(new Double(2));
    transactionEmptyType.setType("");
    transactionEmptyType.setParentId(new Long(2));

    transactionValidator.validate(transactionEmptyType);
  }

  @Test
  public void testValidateWithParentTransaction() throws InvalidTransactionException {
    Transaction transactionWithParent = new Transaction();
    transactionWithParent.setId(new Long(1));
    transactionWithParent.setAmout(new Double(2));
    transactionWithParent.setType("type");
    transactionWithParent.setParentId(new Long(2));

    transactionValidator.validate(transactionWithParent);
  }

  @Test
  public void testValidateWithoutParentTransaction() throws InvalidTransactionException {
    Transaction transactionWithoutParent = new Transaction();
    transactionWithoutParent.setId(new Long(1));
    transactionWithoutParent.setAmout(new Double(2));
    transactionWithoutParent.setType("type");

    transactionValidator.validate(transactionWithoutParent);
  }
}
