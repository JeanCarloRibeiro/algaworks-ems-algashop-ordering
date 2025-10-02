package com.algashop.ordering.infrastructure.persistence.disassembler;

import com.algashop.ordering.domain.model.entity.Order;
import com.algashop.ordering.domain.model.enums.OrderStatus;
import com.algashop.ordering.domain.model.enums.PaymentMethod;
import com.algashop.ordering.domain.model.valueobject.Money;
import com.algashop.ordering.domain.model.valueobject.Quantity;
import com.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class OrderPersistenceEntityDisassembler {
  public Order toDomainEntity(OrderPersistenceEntity orderPersistence) {
    return Order.existing()
            .id(new OrderId(orderPersistence.getId()))
            .customerId(new CustomerId(orderPersistence.getCustomerId()))
            .totalAmount(new Money(orderPersistence.getTotalAmount()))
            .totalItems(new Quantity(orderPersistence.getTotalItems()))
            .status(OrderStatus.valueOf(orderPersistence.getStatus()))
            .paymentMethod(PaymentMethod.valueOf(orderPersistence.getPaymentMethod()))
            .placedAt(orderPersistence.getPlacedAt())
            .paidAt(orderPersistence.getPaidAt())
            .canceledAt(orderPersistence.getCanceledAt())
            .readyAt(orderPersistence.getReadAt())
            .items(new HashSet<>())
            .build();
  }
}
