package com.algashop.ordering.domain.model.entity;

import com.algashop.ordering.domain.model.entity.databuilder.OrderTestDataBuilder;
import com.algashop.ordering.domain.model.enums.OrderStatus;
import com.algashop.ordering.domain.model.exception.OrderStatusCannotBeChangedException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

class OrderMarkAsReadyTest {

  @Test
  void givenPaidOrderWhenReadyShouldChangeToReady() {
    Order order = OrderTestDataBuilder.Order().orderStatus(OrderStatus.PAID).build();
    order.markAsReady();

    Assertions.assertThat(order.status()).isEqualTo(OrderStatus.READY);
    Assertions.assertThat(order.readyAt()).isNotNull();
  }

  @Test
  void givenPlacedOrderWhenReadyShouldReturnException() {
    Order order = OrderTestDataBuilder.Order().orderStatus(OrderStatus.PLACED).build();

    ThrowableAssert.ThrowingCallable callable = order::markAsReady;
    Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class).isThrownBy(callable);
  }

}