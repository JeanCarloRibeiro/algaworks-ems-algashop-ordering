package com.algashop.ordering.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@ToString(of = "id")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemPersistenceEntity {
  @Id
  @EqualsAndHashCode.Include
  private Long id;
  private UUID productId;
  private String productName;
  private BigDecimal price;
  private Integer quantity;
  private BigDecimal totalAmount;

  @ManyToOne(optional = false)
  private OrderPersistenceEntity order;

  public Long getOrderId() {
    if (getOrder() == null) {
      return null;
    }
    return getOrder().getId();
  }
}
