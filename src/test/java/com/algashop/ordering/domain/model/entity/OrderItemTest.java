package com.algashop.ordering.domain.model.entity;

import com.algashop.ordering.domain.model.entity.databuilder.ProductTestDataBuilder;
import com.algashop.ordering.domain.model.valueobject.Product;
import com.algashop.ordering.domain.model.valueobject.Quantity;
import com.algashop.ordering.domain.model.valueobject.id.OrderId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderItemTest {

  @Test
  void shouldGenerateBrandNewOrderItem() {
    Product product = ProductTestDataBuilder.productAltMousePad().build();

    OrderId orderId = new OrderId();
    Quantity quantity = new Quantity(1);

    OrderItem orderItem = OrderItem.brandNew()
            .orderId(orderId)
            .product(product)
            .quantity(quantity)
            .build();

    Assertions.assertWith(orderItem,
            o -> Assertions.assertThat(o.id()).isNotNull(),
            o -> Assertions.assertThat(o.orderId()).isEqualTo(orderId),
            o -> Assertions.assertThat(o.productId()).isEqualTo(product.id()),
            o -> Assertions.assertThat(o.productName()).isEqualTo(product.name()),
            o -> Assertions.assertThat(o.quantity()).isEqualTo(quantity),
            o -> Assertions.assertThat(o.orderId()).isEqualTo(orderId)
    );
  }

}