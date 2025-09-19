package com.algashop.ordering.domain.exception;

import com.algashop.ordering.domain.enums.OrderStatus;
import com.algashop.ordering.domain.valueobject.id.OrderId;

import static com.algashop.ordering.domain.exception.ErrorMessages.ERROR_ORDER_STATUS_CANNOT_BE_CHANGED;

public class OrderStatusCannotBeChangedException extends DomainException {
  public OrderStatusCannotBeChangedException(OrderId id, OrderStatus status, OrderStatus newStatus) {
    super(String.format(ERROR_ORDER_STATUS_CANNOT_BE_CHANGED, id, status, newStatus));
  }
}
