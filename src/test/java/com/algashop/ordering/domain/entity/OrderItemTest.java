package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.valueobject.Money;
import com.algashop.ordering.domain.valueobject.Product;
import com.algashop.ordering.domain.valueobject.ProductName;
import com.algashop.ordering.domain.valueobject.Quantity;
import com.algashop.ordering.domain.valueobject.id.OrderId;
import com.algashop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderItemTest {

  @Test
  void shouldGenerate() {
    Product product = ProductTestDataBuilder.productAltMousePad().build();

    OrderItem orderItem = OrderItem.brandNew()
            .orderId(new OrderId())
            .product(product)
            .quantity(new Quantity(1))
            .build();

    Assertions.assertThat(orderItem.orderId()).isNotNull();
    Assertions.assertWith(orderItem,
            o -> Assertions.assertThat(o.orderId().value()).isNotNull(),
            o -> Assertions.assertThat(o.productName().value()).isEqualTo("Mouse Pad")
    );
  }

}