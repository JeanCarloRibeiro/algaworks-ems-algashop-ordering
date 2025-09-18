package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.valueobject.Money;
import com.algashop.ordering.domain.valueobject.ProductName;
import com.algashop.ordering.domain.valueobject.Quantity;
import com.algashop.ordering.domain.valueobject.id.OrderId;
import com.algashop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderItemTest {

  @Test
  void shouldGenerate() {
    OrderItem orderItem = OrderItem.brandNew()
            .orderId(new OrderId())
            .productId(new ProductId())
            .productName(new ProductName("Mouse pad"))
            .quantity(new Quantity(1))
            .price(new Money("100"))
            .build();

    Assertions.assertThat(orderItem.orderId()).isNotNull();
    Assertions.assertWith(orderItem,
            o -> Assertions.assertThat(o.orderId().value()).isNotNull(),
            o -> Assertions.assertThat(o.productName().value()).isEqualTo("Mouse pad")
    );
  }

}