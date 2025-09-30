package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.entity.databuilder.ShoppingCartItemTestDataBuilder;
import com.algashop.ordering.domain.valueobject.Money;
import com.algashop.ordering.domain.valueobject.Quantity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ShoppingCartItemTest {

  @Test
  void shouldGenerateShoppingCartItem() {
    ShoppingCartItem cart = ShoppingCartItemTestDataBuilder.shoppingCartItem().build();

    Assertions.assertWith(cart,
            o -> Assertions.assertThat(o.id()).isNotNull(),
            o -> Assertions.assertThat(o.shoppingCartId()).isNotNull(),
            o -> Assertions.assertThat(o.product()).isNotNull(),
            o -> Assertions.assertThat(o.quantity()).isNotNull(),
            o -> Assertions.assertThat(o.totalAmount()).isNotNull(),
            o -> Assertions.assertThat(o.available()).isTrue(),
            o -> Assertions.assertThat(o.totalAmount()).isEqualTo(new Money("1000"))
    );
  }

  @Test
  void givenShoppingCartItemWhenChangeItemQuantityShouldRecalculate() {
    ShoppingCartItem cart = ShoppingCartItemTestDataBuilder.shoppingCartItem().build();
    cart.changeQuantity(new Quantity(1));

    Assertions.assertWith(cart,
            o -> Assertions.assertThat(o.totalAmount()).isEqualTo(new Money("1000")),
            o -> Assertions.assertThat(o.quantity()).isEqualTo(new Quantity(1))
    );
  }

  @Test
  void givenShoppingCartItemWhenRefreshItemShouldRecalculate() {
    ShoppingCartItem cart = ShoppingCartItemTestDataBuilder.shoppingCartItem().build();
    cart.refresh(cart.product());

    Assertions.assertWith(cart,
            o -> Assertions.assertThat(o.totalAmount()).isEqualTo(new Money("1000")),
            o -> Assertions.assertThat(o.quantity()).isEqualTo(new Quantity(1))
    );
  }

}