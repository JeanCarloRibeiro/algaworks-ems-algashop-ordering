package com.algashop.ordering.infrastructure.persistence.entity.databuilder;

import com.algashop.ordering.domain.model.entity.databuilder.CustomerTestDataBuilder;
import com.algashop.ordering.domain.model.utility.IdGenerator;
import com.algashop.ordering.domain.model.valueobject.Money;
import com.algashop.ordering.infrastructure.persistence.entity.ShoppingCartItemPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

import static com.algashop.ordering.domain.model.entity.databuilder.ShoppingCartTestDataBuilder.DEFAULT_SHOPPING_CART_ID;

public class ShoppingCartPersistenceEntityTestDataBuilder {

  private ShoppingCartPersistenceEntityTestDataBuilder() {
  }

  public static ShoppingCartPersistenceEntity.ShoppingCartPersistenceEntityBuilder existingShopping() {
    return ShoppingCartPersistenceEntity.builder()
            .id(DEFAULT_SHOPPING_CART_ID.value())
            .customerId(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID.value())
            .totalAmount(new Money("1000").value())
            .totalItems(1)
            .createdAt(OffsetDateTime.now())
            .items(Set.of(existingItem().build(), existingItemAlt().build()));
  }

  public static ShoppingCartItemPersistenceEntity.ShoppingCartItemPersistenceEntityBuilder existingItem() {
    return ShoppingCartItemPersistenceEntity.builder()
            .id(IdGenerator.generateTimeBasedUUID())
            .price(new BigDecimal(500))
            .quantity(2)
            .totalAmount(new BigDecimal(1000))
            .productName("Notebook")
            .inStock(true)
            .productId(IdGenerator.generateTimeBasedUUID());
  }

  public static ShoppingCartItemPersistenceEntity.ShoppingCartItemPersistenceEntityBuilder existingItemAlt() {
    return ShoppingCartItemPersistenceEntity.builder()
            .id(IdGenerator.generateTimeBasedUUID())
            .price(new BigDecimal(250))
            .quantity(1)
            .totalAmount(new BigDecimal(250))
            .productName("Mouse pad")
            .inStock(true)
            .productId(IdGenerator.generateTimeBasedUUID());
  }

}
