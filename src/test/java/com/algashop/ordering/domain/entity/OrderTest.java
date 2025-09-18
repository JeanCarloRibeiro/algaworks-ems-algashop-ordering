package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.valueobject.id.CustomerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderTest {

  @Test
  void shouldGenerate() {
    Order order = Order.draft(new CustomerId());
    Assertions.assertThat(order.customerId()).isNotNull();
  }
}