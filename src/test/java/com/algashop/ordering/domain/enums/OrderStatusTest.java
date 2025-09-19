package com.algashop.ordering.domain.enums;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderStatusTest {

  @Test
  void canChangeTo() {
    Assertions.assertThat(OrderStatus.DRAFT.canChangeTo(OrderStatus.PLACED)).isTrue();
    Assertions.assertThat(OrderStatus.DRAFT.canChangeTo(OrderStatus.CANCELED)).isTrue();
    Assertions.assertThat(OrderStatus.PAID.canNotChangeTo(OrderStatus.DRAFT)).isTrue();
  }
}