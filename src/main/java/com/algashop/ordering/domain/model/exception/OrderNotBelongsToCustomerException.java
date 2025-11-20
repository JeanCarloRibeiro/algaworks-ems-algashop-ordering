package com.algashop.ordering.domain.model.exception;

public class OrderNotBelongsToCustomerException extends DomainException {

  public OrderNotBelongsToCustomerException() {
  }

  public OrderNotBelongsToCustomerException(String message) {
    super(message);
  }

  public OrderNotBelongsToCustomerException(String message, Throwable cause) {
    super(message, cause);
  }
}
