package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.enums.OrderStatus;
import com.algashop.ordering.domain.exception.OrderCannotBeCanceledException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

class OrderCancelTest {

  @Test
  void givenDraftOrderShouldCancel() {
    Order order = OrderTestDataBuilder.Order().build();
    order.cancel();

    Assertions.assertThat(order.isCanceled()).isTrue();
    Assertions.assertThat(order.canceledAt()).isNotNull();
  }
  @Test
  void givenPlacedOrderShouldCancel() {
    Order order = OrderTestDataBuilder.Order().orderStatus(OrderStatus.PLACED).build();
    order.cancel();

    Assertions.assertThat(order.isCanceled()).isTrue();
    Assertions.assertThat(order.canceledAt()).isNotNull();
  }

  @Test
  void givenPaidOrderShouldCancel() {
    Order order = OrderTestDataBuilder.Order().orderStatus(OrderStatus.PAID).build();
    order.cancel();

    Assertions.assertThat(order.isCanceled()).isTrue();
    Assertions.assertThat(order.canceledAt()).isNotNull();
  }

  @Test
  void givenCanceledOrderShouldThrowExceptionWhenOrderAlreadyCanceled() {
    Order order = OrderTestDataBuilder.Order().orderStatus(OrderStatus.CANCELED).build();

    ThrowableAssert.ThrowingCallable callable = order::cancel;
    Assertions.assertThatExceptionOfType(OrderCannotBeCanceledException.class).isThrownBy(callable);
  }

}