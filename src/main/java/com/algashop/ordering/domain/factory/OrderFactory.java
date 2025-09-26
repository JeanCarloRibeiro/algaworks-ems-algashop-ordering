package com.algashop.ordering.domain.factory;

import com.algashop.ordering.domain.entity.Order;
import com.algashop.ordering.domain.enums.PaymentMethod;
import com.algashop.ordering.domain.valueobject.Billing;
import com.algashop.ordering.domain.valueobject.Product;
import com.algashop.ordering.domain.valueobject.Quantity;
import com.algashop.ordering.domain.valueobject.Shipping;
import com.algashop.ordering.domain.valueobject.id.CustomerId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderFactory {

  public static Order filled (
          CustomerId customerId,
          Shipping shipping,
          Billing billing,
          PaymentMethod paymentMethod,
          Product product,
          Quantity quantity) {

    Objects.requireNonNull(customerId);
    Objects.requireNonNull(shipping);
    Objects.requireNonNull(billing);
    Objects.requireNonNull(paymentMethod);
    Objects.requireNonNull(product);
    Objects.requireNonNull(quantity);

    Order order = Order.draft(customerId);
    order.changeBilling(billing);
    order.changeShipping(shipping);
    order.changePaymentMethod(paymentMethod);

    order.addItem(product, quantity);

    return order;
  }

}
