package com.algashop.ordering.domain.model.entity.databuilder;

import com.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algashop.ordering.domain.model.valueobject.Money;
import com.algashop.ordering.domain.model.valueobject.Quantity;
import com.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;

import java.time.OffsetDateTime;
import java.util.Set;

public class ShoppingCartTestDataBuilder {

  public static ShoppingCart.ExistingShoppingCartBuilder shoppingCart() {
    return ShoppingCart.existing()
            .id(new ShoppingCartId())
            .customerId(new CustomerId())
            .totalAmount(new Money("1"))
            .totalItems(new Quantity(1))
            .createdAt(OffsetDateTime.now())
            .items(Set.of(ShoppingCartItemTestDataBuilder.shoppingCartItem().build()));
  }

  public static ShoppingCart.ExistingShoppingCartBuilder shoppingCartUnavailable() {
    return ShoppingCart.existing()
            .id(new ShoppingCartId())
            .customerId(new CustomerId())
            .totalAmount(new Money("1"))
            .totalItems(new Quantity(1))
            .createdAt(OffsetDateTime.now())
            .items(Set.of(ShoppingCartItemTestDataBuilder.shoppingCartItemUnavailable().build()));
  }

}
