package com.algashop.ordering.domain.model.exception;

public class CustomerEmailInUseException extends DomainException {
  public CustomerEmailInUseException() {
  }

  public CustomerEmailInUseException(String message) {
    super(message);
  }

  public CustomerEmailInUseException(String message, Throwable cause) {
    super(message, cause);
  }
}
