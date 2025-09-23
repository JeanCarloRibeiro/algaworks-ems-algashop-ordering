package com.algashop.ordering.domain.exception;

import com.algashop.ordering.domain.valueobject.id.OrderId;

public class OrderInvalidShippingDeliveryDateException extends DomainException {
  public OrderInvalidShippingDeliveryDateException(OrderId id) {
    super(String.format(ErrorMessages.ERROR_DELIVERY_DATE_CANNOT_BE_IN_THE_PAST, id));
  }
}
