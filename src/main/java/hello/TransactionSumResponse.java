package hello;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionSumResponse {

  private Double sum;

  public TransactionSumResponse(Double sum) { this.sum = sum; }

  @JsonProperty("sum")
  public Double getSum() { return this.sum; }
}
