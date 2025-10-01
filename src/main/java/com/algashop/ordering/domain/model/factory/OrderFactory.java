package com.algashop.ordering.domain.model.factory;

import com.algashop.ordering.domain.model.entity.Order;
import com.algashop.ordering.domain.model.enums.PaymentMethod;
import com.algashop.ordering.domain.model.valueobject.Billing;
import com.algashop.ordering.domain.model.valueobject.Product;
import com.algashop.ordering.domain.model.valueobject.Quantity;
import com.algashop.ordering.domain.model.valueobject.Shipping;
import com.algashop.ordering.domain.model.valueobject.id.CustomerId;
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
