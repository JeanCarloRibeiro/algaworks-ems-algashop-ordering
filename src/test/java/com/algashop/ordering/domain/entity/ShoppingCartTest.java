package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.entity.databuilder.ProductTestDataBuilder;
import com.algashop.ordering.domain.entity.databuilder.ShoppingCartTestDataBuilder;
import com.algashop.ordering.domain.exception.ProductOutOfStockException;
import com.algashop.ordering.domain.exception.ShoppingCartDoesNotContainItemException;
import com.algashop.ordering.domain.valueobject.Money;
import com.algashop.ordering.domain.valueobject.Product;
import com.algashop.ordering.domain.valueobject.ProductName;
import com.algashop.ordering.domain.valueobject.Quantity;
import com.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algashop.ordering.domain.valueobject.id.ProductId;
import com.algashop.ordering.domain.valueobject.id.ShoppingCartItemId;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

class ShoppingCartTest {

  @Test
  void shouldGenerateShoppingCart() {
    ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());

    Assertions.assertWith(cart,
            o -> Assertions.assertThat(o.id()).isNotNull(),
            o -> Assertions.assertThat(o.customerId()).isNotNull(),
            o -> Assertions.assertThat(o.items()).isEmpty(),
            o -> Assertions.assertThat(o.createdAt()).isNotNull(),
            o -> Assertions.assertThat(o.totalItems()).isEqualTo(Quantity.ZERO),
            o -> Assertions.assertThat(o.totalAmount()).isEqualTo(Money.ZERO)
    );
  }

  @Test
  void shouldGenerateShoppingCartEquals() {
    ShoppingCart cart = ShoppingCart.startShopping(new CustomerId()).toBuilder().build();
    ShoppingCart cartAlt = cart.toBuilder().build();

    Assertions.assertWith(cart, o -> Assertions.assertThat(o.id()).isNotEqualTo(cartAlt.id()));
  }

  @Test
  void shouldAddItem() {
    ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());

    Product product = ProductTestDataBuilder.productAltMousePad().build();
    ProductId productId = product.id();

    cart.addItem(product, new Quantity(1));

    Assertions.assertThat(cart.items()).isNotEmpty();
    Assertions.assertThat(cart.items()).hasSize(1);

    ShoppingCartItem cartItem = cart.items().iterator().next();
    Assertions.assertWith(cartItem,
            i -> Assertions.assertThat(i.id()).isNotNull(),
            i -> Assertions.assertThat(i.product().id()).isEqualTo(productId),
            i -> Assertions.assertThat(i.product().name()).isEqualTo(new ProductName("Mouse Pad")),
            i -> Assertions.assertThat(i.product().price()).isEqualTo(new Money("100")),
            i -> Assertions.assertThat(i.quantity()).isEqualTo(new Quantity(1))
    );

  }

  @Test
  void givenShoppingCartWhenRemoveItemShouldRecalculate() {
    ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());

    Product product = ProductTestDataBuilder.productAltMousePad().build();
    cart.addItem(product, new Quantity(1));

    Product memory = ProductTestDataBuilder.productAltRamMemory().build();
    cart.addItem(memory, new Quantity(1));

    ShoppingCartItem cartItem = cart.findItem(memory);
    cart.removeItem(cartItem.id());

    Assertions.assertWith(cart,
            i -> Assertions.assertThat(i.totalAmount()).isEqualTo(new Money("100")),
            i -> Assertions.assertThat(i.totalItems()).isEqualTo(new Quantity(1))
    );

  }

  @Test
  void givenShoppingCartWhenRemoveItemInvalidShouldThrowException() {
    ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());

    Product product = ProductTestDataBuilder.productAltMousePad().build();
    cart.addItem(product, new Quantity(1));

    Product memory = ProductTestDataBuilder.productAltRamMemory().build();
    cart.addItem(memory, new Quantity(1));

    ThrowableAssert.ThrowingCallable callable = () -> cart.removeItem(new ShoppingCartItemId());
    Assertions.assertThatExceptionOfType(ShoppingCartDoesNotContainItemException.class).isThrownBy(callable);
  }

  @Test
  void givenShoppingCartWhenEmptyShouldClearItems() {
    ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());
    cart.empty();
    Assertions.assertThat(cart.isEmpty()).isTrue();
  }

  @Test
  void givenShoppingCartWhenChangeItemQuantityShouldRecalculate() {
    ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());

    Product product = ProductTestDataBuilder.productAltMousePad().build();
    cart.addItem(product, new Quantity(1));

    ShoppingCartItem cartItem = cart.items().iterator().next();
    cart.changeItemQuantity(cartItem.id(), new Quantity(5));

    Assertions.assertWith(cart,
            o -> Assertions.assertThat(o.totalAmount()).isEqualTo(new Money("500")),
            o -> Assertions.assertThat(o.totalItems()).isEqualTo(new Quantity(5))
    );

  }

  @Test
  void givenShoppingCartWhenChangeItemQuantityWithItemNotExistShouldThrowException() {
    ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());

    Product product = ProductTestDataBuilder.product().build();
    cart.addItem(product, new Quantity(1));

    Assertions.assertThatExceptionOfType(ShoppingCartDoesNotContainItemException.class)
            .isThrownBy(() -> cart.changeItemQuantity(new ShoppingCartItemId(), new Quantity(5)));
  }

  @Test
  void givenShoppingCartProductOutOfStockWhenTryToAddAnOrderShouldReturnException() {
    ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());

    Product product = ProductTestDataBuilder.productUnavailable().build();

    ThrowableAssert.ThrowingCallable callable = () -> cart.addItem(product, new Quantity(1));
    Assertions.assertThatExceptionOfType(ProductOutOfStockException.class).isThrownBy(callable);
  }

  @Test
  void givenShoppingCartWhenRefreshItemShouldRecalculate() {
    ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());

    Product product = ProductTestDataBuilder.productAltMousePad().build();
    cart.addItem(product, new Quantity(1));

    Product refresh = product.toBuilder().price(new Money("1")).build();
    cart.refreshItem(refresh);

    Assertions.assertWith(cart,
            o -> Assertions.assertThat(o.totalAmount()).isEqualTo(new Money("1")),
            o -> Assertions.assertThat(o.totalItems()).isEqualTo(new Quantity(1))
    );
  }

  @Test
  void givenShoppingCartWhenFindItemByCartIdShouldReturn() {
    ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());

    Product product = ProductTestDataBuilder.productAltMousePad().build();
    cart.addItem(product, new Quantity(1));

    ShoppingCartItemId cartItemId = cart.items().iterator().next().id();
    ShoppingCartItem cartItem = cart.findItem(cartItemId);

    Assertions.assertWith(cartItem,
            o -> Assertions.assertThat(o.id()).isNotNull(),
            o -> Assertions.assertThat(o.available()).isTrue(),
            o -> Assertions.assertThat(o.totalAmount()).isEqualTo(new Money("100"))
    );
  }

  @Test
  void givenShoppingCartWhenContainsUnavailableItemsShouldReturnTrue() {
    ShoppingCart cart = ShoppingCartTestDataBuilder.shoppingCartUnavailable().build();

    Assertions.assertWith(cart,
            o -> Assertions.assertThat(o.id()).isNotNull(),
            o -> Assertions.assertThat(o.containsUnavailableItems()).isTrue()
    );
  }

}