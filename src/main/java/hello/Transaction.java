package hello;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
}
