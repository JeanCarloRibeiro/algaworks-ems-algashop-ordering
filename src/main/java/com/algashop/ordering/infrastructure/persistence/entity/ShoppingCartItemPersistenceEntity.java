package com.algashop.ordering.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "\"shopping_cart_item\"")
@EntityListeners(AuditingEntityListener.class)
public class ShoppingCartItemPersistenceEntity {
  @Id
  @EqualsAndHashCode.Include
  private UUID id; //UUID

  @ManyToOne(optional = false)
  private ShoppingCartPersistenceEntity shoppingCart;

  private UUID productId;
  private String productName;
  private BigDecimal price;
  private Boolean inStock;

  private Integer quantity;
  private BigDecimal totalAmount;
  private Boolean available;

  @CreatedBy
  private UUID createdByUserId;
  @LastModifiedDate
  private OffsetDateTime lastModifiedAt;
  @LastModifiedBy
  private UUID lastModifiedByUserId;

  @Version
  private Long version;

  @Builder
  public ShoppingCartItemPersistenceEntity(UUID id, ShoppingCartPersistenceEntity shoppingCart,
                                           UUID productId, String productName, BigDecimal price,
                                           Boolean inStock, Integer quantity, BigDecimal totalAmount,
                                           Boolean available, UUID createdByUserId,
                                           OffsetDateTime lastModifiedAt, UUID lastModifiedByUserId,
                                           Long version) {
    this.id = id;
    this.shoppingCart = shoppingCart;
    this.productId = productId;
    this.productName = productName;
    this.price = price;
    this.inStock = inStock;
    this.quantity = quantity;
    this.totalAmount = totalAmount;
    this.available = available;
    this.createdByUserId = createdByUserId;
    this.lastModifiedAt = lastModifiedAt;
    this.lastModifiedByUserId = lastModifiedByUserId;
    this.version = version;
  }
}
