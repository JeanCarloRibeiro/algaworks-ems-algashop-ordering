package com.algashop.ordering.domain.model.exception;

import com.algashop.ordering.domain.model.valueobject.id.ShoppingCartItemId;

public class ShoppingCartItemIncompatibleProductException extends DomainException {
  public ShoppingCartItemIncompatibleProductException(ShoppingCartItemId id) {
    super(String.format(ErrorMessages.ERROR_CART_ITEM_PRODUCT_INCOMPATIBLE, id));
  }
}
