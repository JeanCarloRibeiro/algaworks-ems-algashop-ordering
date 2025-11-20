package com.algashop.ordering.domain.model.exception;

public class CantAddLoyaltPointOrderIsNotReadyException extends DomainException {

  public CantAddLoyaltPointOrderIsNotReadyException() {
  }

  public CantAddLoyaltPointOrderIsNotReadyException(String message) {
    super(message);
  }

  public CantAddLoyaltPointOrderIsNotReadyException(String message, Throwable cause) {
    super(message, cause);
  }
}
