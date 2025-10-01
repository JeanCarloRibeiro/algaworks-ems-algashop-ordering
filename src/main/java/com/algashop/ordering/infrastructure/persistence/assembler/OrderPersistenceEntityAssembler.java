package com.algashop.ordering.infrastructure.persistence.assembler;

import com.algashop.ordering.domain.model.entity.Order;
import com.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderPersistenceEntityAssembler {

  public OrderPersistenceEntity fromDomain(Order order) {
    return merge(new OrderPersistenceEntity(), order);
  }

  public OrderPersistenceEntity merge(OrderPersistenceEntity entity, Order order) {
    entity.setId(order.id().value().toLong());
    entity.setCustomerId(order.customerId().value());
    entity.setTotalAmount(order.totalAmount().value());
    entity.setTotalItems(order.totalItems().value());
    entity.setStatus(order.status().name());
    entity.setPaymentMethod(order.paymentMethod().name());
    entity.setPlacedAt(order.placedAt());
    entity.setPaidAt(order.paidAt());
    entity.setCanceledAt(order.canceledAt());
    entity.setReadAt(order.readyAt());
    return entity;
  }

}
