package com.algashop.ordering.domain.model.exception;

public class CustomerArchivedException extends DomainException {
  public CustomerArchivedException() {
    super(ErrorMessages.ERROR_CUSTOMER_ARCHIVED);
  }

  public CustomerArchivedException(String message, Throwable cause) {
    super(message, cause);
  }
}
