package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.entity.databuilder.OrderTestDataBuilder;
import com.algashop.ordering.domain.enums.OrderStatus;
import com.algashop.ordering.domain.exception.OrderCannotBeEditedException;
import com.algashop.ordering.domain.exception.OrderDoesNotContainOrderItemException;
import com.algashop.ordering.domain.valueobject.id.OrderItemId;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

class OrderRemoveItemTest {

  @Test
  void givenDraftOrderShouldRemoveItem() {
    Order order = OrderTestDataBuilder.Order().build();

    OrderItem orderItem = order.items().iterator().next();
    order.removeItem(orderItem.id());

    Assertions.assertThat(order.items()).hasSize(1);
  }

  @Test
  void givenPlacedOrderShouldNotAllowRemoveItem() {
    Order order = OrderTestDataBuilder.Order().orderStatus(OrderStatus.PLACED).build();

    OrderItem orderItem = order.items().iterator().next();
    ThrowableAssert.ThrowingCallable callable = () -> order.removeItem(orderItem.id());

    Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class).isThrownBy(callable);
  }

  @Test
  void givenDraftOrderShouldReturnExceptionWhenOrderItemNotExists() {
    Order order = OrderTestDataBuilder.Order().build();

    ThrowableAssert.ThrowingCallable callable = () -> order.removeItem(new OrderItemId());
    Assertions.assertThatExceptionOfType(OrderDoesNotContainOrderItemException.class).isThrownBy(callable);
  }

}