package com.jordanabderrachid.n26;

import com.jordanabderrachid.n26.datastores.TransactionStore;
import com.jordanabderrachid.n26.datastores.TransactionStoreMemory;
import com.jordanabderrachid.n26.services.TransactionService;
import com.jordanabderrachid.n26.validators.TransactionValidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

  public static void main(String[] args) { SpringApplication.run(Application.class, args); }

  @Bean
  public TransactionService getTransactionService() {
    TransactionStore transactionStore = new TransactionStoreMemory();
    return new TransactionService(transactionStore);
  }

  @Bean
  public TransactionValidator getTransactionValidator() {
    return new TransactionValidator();
  }
}
