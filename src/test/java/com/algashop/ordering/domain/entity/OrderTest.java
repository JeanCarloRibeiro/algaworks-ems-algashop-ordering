package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.exception.OrderStatusCannotBeChangedException;
import com.algashop.ordering.domain.valueobject.Money;
import com.algashop.ordering.domain.valueobject.ProductName;
import com.algashop.ordering.domain.valueobject.Quantity;
import com.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algashop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class OrderTest {

  @Test
  void shouldGenerate() {
    Order order = Order.draft(new CustomerId());
    Assertions.assertThat(order.customerId()).isNotNull();
  }

  @Test
  void shouldAddItem() {
    Order order = Order.draft(new CustomerId());
    ProductId productId = new ProductId();

    order.addItem(
            productId,
            new ProductName("Mouse pad"),
            new Money("100"),
            new Quantity(1)
    );
    Assertions.assertThat(order.items()).isNotEmpty();
    Assertions.assertThat(order.items()).hasSize(1);

    OrderItem orderItem = order.items().iterator().next();

    Assertions.assertWith(orderItem,
            i -> Assertions.assertThat(i.id()).isNotNull(),
            i -> Assertions.assertThat(i.productId()).isEqualTo(productId),
            i -> Assertions.assertThat(i.productName()).isEqualTo(new ProductName("Mouse pad")),
            i -> Assertions.assertThat(i.price()).isEqualTo(new Money("100")),
            i -> Assertions.assertThat(i.quantity()).isEqualTo(new Quantity(1))
    );

  }

  @Test
  void shouldGenerateExceptionWhenTryToChangeItemSet() {
    Order order = Order.draft(new CustomerId());
    ProductId productId = new ProductId();

    order.addItem(
            productId,
            new ProductName("Mouse pad"),
            new Money("100"),
            new Quantity(1)
    );

    Set<OrderItem> items = order.items();

    Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
            .isThrownBy(items::clear);
  }

  @Test
  void shouldCalculateTotals() {
    Order order = Order.draft(new CustomerId());
    order.addItem(
            new ProductId(),
            new ProductName("Mouse pad"),
            new Money("100"),
            new Quantity(1)
    );
    order.addItem(
            new ProductId(),
            new ProductName("Monitor"),
            new Money("1000"),
            new Quantity(1)
    );
    Assertions.assertWith(order,
            i -> Assertions.assertThat(i.totalAmount()).isEqualTo(new Money("1100")),
            i -> Assertions.assertThat(i.totalItems()).isEqualTo(new Quantity(2))
    );

  }

  @Test
  void givenDraftOrderWhenPlaceShouldChanceToPlaced() {
    Order order = Order.draft(new CustomerId());
    order.place();
    Assertions.assertThat(order.isPlaced()).isTrue();
  }

  @Test
  void givenPlacedOrderWhenTryToPlaceGenerateException() {
    Order order = Order.draft(new CustomerId());
    order.place();
    Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
            .isThrownBy(order::place);
  }
}