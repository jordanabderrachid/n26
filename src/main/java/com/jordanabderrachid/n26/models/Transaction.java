package com.jordanabderrachid.n26.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a transaction.
 *
 * @author jordanabderrachid
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {
  private Long id;

  private Double amount;

  private String type;

  private Long parentId;

  public Transaction() {}

  public boolean hasParentTransaction() { return this.parentId != null; }

  @JsonIgnore
  public Long getId() { return this.id; }

  @JsonIgnore
  public void setId(Long id) { this.id = id; }

  @JsonProperty("amount")
  public Double getAmount() { return this.amount; }

  @JsonProperty("amount")
  public void setAmout(Double amount) { this.amount = amount; }

  @JsonProperty("type")
  public String getType() { return this.type; }

  @JsonProperty("type")
  public void setType(String type) { this.type = type; }

  @JsonProperty("parent_id")
  public void setParentId(Long parentId) { this.parentId = parentId;}

  @JsonProperty("parent_id")
  public Long getParentId() { return this.parentId; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Transaction that = (Transaction) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
    if (type != null ? !type.equals(that.type) : that.type != null) return false;
    return parentId != null ? parentId.equals(that.parentId) : that.parentId == null;

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (amount != null ? amount.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
    return result;
  }
}
