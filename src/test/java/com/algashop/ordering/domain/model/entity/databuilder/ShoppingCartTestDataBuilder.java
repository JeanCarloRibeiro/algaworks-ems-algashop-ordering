package com.algashop.ordering.domain.model.entity.databuilder;

import com.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algashop.ordering.domain.model.valueobject.Money;
import com.algashop.ordering.domain.model.valueobject.Quantity;
import com.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;

import java.time.OffsetDateTime;
import java.util.Set;

import static com.algashop.ordering.domain.model.entity.databuilder.CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

public class ShoppingCartTestDataBuilder {

  public static final ShoppingCartId DEFAULT_SHOPPING_CART_ID = new ShoppingCartId();

  public ShoppingCartTestDataBuilder() {
  }

  public static ShoppingCart.ExistingShoppingCartBuilder shoppingCart() {
    return ShoppingCart.existing()
            .id(DEFAULT_SHOPPING_CART_ID)
            .customerId(DEFAULT_CUSTOMER_ID)
            .totalAmount(new Money("1"))
            .totalItems(new Quantity(1))
            .createdAt(OffsetDateTime.now())
            .items(Set.of(ShoppingCartItemTestDataBuilder.shoppingCartItem().build()));
  }

  public static ShoppingCart.ExistingShoppingCartBuilder shoppingCartUnavailable() {
    return ShoppingCart.existing()
            .id(DEFAULT_SHOPPING_CART_ID)
            .customerId(new CustomerId())
            .totalAmount(new Money("1"))
            .totalItems(new Quantity(1))
            .createdAt(OffsetDateTime.now())
            .items(Set.of(ShoppingCartItemTestDataBuilder.shoppingCartItemUnavailable().build()));
  }

}
