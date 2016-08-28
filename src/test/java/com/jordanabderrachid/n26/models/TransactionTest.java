package com.jordanabderrachid.n26.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TransactionTest {

  @Test
  public void testHasParentTransaction() {
    Transaction transactionWithParent = new Transaction();
    transactionWithParent.setParentId(new Long(1));

    assertThat(transactionWithParent.hasParentTransaction()).isTrue();

    Transaction transactionWithoutParent = new Transaction();
    assertThat(transactionWithoutParent.hasParentTransaction()).isFalse();
  }
}