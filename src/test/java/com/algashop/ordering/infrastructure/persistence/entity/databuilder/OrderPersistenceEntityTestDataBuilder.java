package com.algashop.ordering.infrastructure.persistence.entity.databuilder;

import com.algashop.ordering.domain.model.utility.IdGenerator;
import com.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity.OrderPersistenceEntityBuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

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
            .placedAt(OffsetDateTime.now());
  }
}
