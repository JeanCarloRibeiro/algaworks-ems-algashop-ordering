package com.algashop.ordering.domain.model.exception;

import com.algashop.ordering.domain.model.valueobject.id.OrderId;

import static com.algashop.ordering.domain.model.exception.ErrorMessages.ERROR_ORDER_CANNOT_BE_CANCELED;

public class OrderCannotBeCanceledException extends DomainException {
  public OrderCannotBeCanceledException(OrderId id) {
    super(String.format(ERROR_ORDER_CANNOT_BE_CANCELED, id));
  }
}
