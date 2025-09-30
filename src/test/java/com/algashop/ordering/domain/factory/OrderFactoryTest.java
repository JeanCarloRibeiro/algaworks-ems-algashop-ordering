package com.algashop.ordering.domain.factory;

import com.algashop.ordering.domain.entity.Order;
import com.algashop.ordering.domain.entity.databuilder.OrderTestDataBuilder;
import com.algashop.ordering.domain.entity.databuilder.ProductTestDataBuilder;
import com.algashop.ordering.domain.enums.PaymentMethod;
import com.algashop.ordering.domain.valueobject.Billing;
import com.algashop.ordering.domain.valueobject.Product;
import com.algashop.ordering.domain.valueobject.Quantity;
import com.algashop.ordering.domain.valueobject.Shipping;
import com.algashop.ordering.domain.valueobject.id.CustomerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderFactoryTest {

  @Test
  void shouldGenerateFilledOrderThatCanBePlaced() {
    Shipping shipping = OrderTestDataBuilder.shipping();
    Billing billing = OrderTestDataBuilder.billing();
    PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
    Product product = ProductTestDataBuilder.product().build();
    Quantity quantity = new Quantity(1);


    CustomerId customerId = new CustomerId();
    Order order = OrderFactory.filled(customerId, shipping, billing,
            paymentMethod, product, quantity);

    Assertions.assertWith(order,
            o -> Assertions.assertThat(o.shipping()).isEqualTo(shipping),
            o -> Assertions.assertThat(o.billing()).isEqualTo(billing),
            o -> Assertions.assertThat(o.paymentMethod()).isEqualTo(paymentMethod),
            o -> Assertions.assertThat(o.items()).isNotEmpty(),
            o -> Assertions.assertThat(o.customerId()).isEqualTo(customerId)
    );
    order.place();

    Assertions.assertThat(order.isPlaced()).isTrue();
  }
}