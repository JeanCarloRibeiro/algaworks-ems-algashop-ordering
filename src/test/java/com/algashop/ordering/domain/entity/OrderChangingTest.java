package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.entity.databuilder.OrderTestDataBuilder;
import com.algashop.ordering.domain.enums.OrderStatus;
import com.algashop.ordering.domain.exception.OrderCannotBeEditedException;
import com.algashop.ordering.domain.valueobject.Shipping;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

class OrderChangingTest {
  @Test
  void givenPlacedOrderShouldReturnNotAllowChange() {
    Order order = OrderTestDataBuilder.Order().orderStatus(OrderStatus.PLACED).build();

    Shipping shipping = OrderTestDataBuilder.shipping();
    ThrowableAssert.ThrowingCallable callable = () -> order.changeShipping(shipping);

    Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class).isThrownBy(callable);
  }

}