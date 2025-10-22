package com.algashop.ordering.infrastructure.persistence.entity.databuilder;

import com.algashop.ordering.domain.model.utility.IdGenerator;
import com.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity.OrderPersistenceEntityBuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

public class OrderPersistenceEntityTestDataBuilder {

  private OrderPersistenceEntityTestDataBuilder() {
  }

  public static OrderPersistenceEntityBuilder existingOrder() {
    return OrderPersistenceEntity.builder()
            .id(IdGenerator.generateTSID().toLong())
            .customerId(IdGenerator.generateTimeBasedUUID())
            .totalItems(2)
            .totalAmount(new BigDecimal(1000))
            .status("DRAFT")
            .paymentMethod("CREDIT_CARD")
            .placedAt(OffsetDateTime.now())
            .items(Set.of(existingItem().build(), existingItemAlt().build()));
  }

  public static OrderItemPersistenceEntity.OrderItemPersistenceEntityBuilder existingItem() {
    return OrderItemPersistenceEntity.builder()
            .id(IdGenerator.generateTSID().toLong())
            .price(new BigDecimal(500))
            .quantity(2)
            .totalAmount(new BigDecimal(1000))
            .productId(IdGenerator.generateTimeBasedUUID())
            .productName("Notebook");
  }

  public static OrderItemPersistenceEntity.OrderItemPersistenceEntityBuilder existingItemAlt() {
    return OrderItemPersistenceEntity.builder()
            .id(IdGenerator.generateTSID().toLong())
            .price(new BigDecimal(250))
            .quantity(1)
            .totalAmount(new BigDecimal(250))
            .productId(IdGenerator.generateTimeBasedUUID())
            .productName("Mouse pad");
  }

}
