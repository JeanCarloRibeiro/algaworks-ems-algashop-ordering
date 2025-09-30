package com.algashop.ordering.domain.entity.databuilder;

import com.algashop.ordering.domain.entity.ShoppingCart;
import com.algashop.ordering.domain.valueobject.Money;
import com.algashop.ordering.domain.valueobject.Quantity;
import com.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algashop.ordering.domain.valueobject.id.ShoppingCartId;

import java.time.OffsetDateTime;
import java.util.Set;

public class ShoppingCartTestDataBuilder {

  public static ShoppingCart.existingShoppingCartBuilder shoppingCart() {
    return ShoppingCart.existing()
            .id(new ShoppingCartId())
            .customerId(new CustomerId())
            .totalAmount(new Money("1"))
            .totalItems(new Quantity(1))
            .createdAt(OffsetDateTime.now())
            .items(Set.of(ShoppingCartItemTestDataBuilder.shoppingCartItem().build()));
  }

  public static ShoppingCart.existingShoppingCartBuilder shoppingCartUnavailable() {
    return ShoppingCart.existing()
            .id(new ShoppingCartId())
            .customerId(new CustomerId())
            .totalAmount(new Money("1"))
            .totalItems(new Quantity(1))
            .createdAt(OffsetDateTime.now())
            .items(Set.of(ShoppingCartItemTestDataBuilder.shoppingCartItemUnavailable().build()));
  }

}
