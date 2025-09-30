package com.algashop.ordering.domain.exception;

import com.algashop.ordering.domain.valueobject.id.ShoppingCartId;

public class ShoppingCartDoesNotContainItemException extends DomainException {
  public ShoppingCartDoesNotContainItemException(ShoppingCartId id) {
    super(String.format(ErrorMessages.ERROR_CART_DOES_NOT_CONTAIN_ITEM, id));
  }
}
