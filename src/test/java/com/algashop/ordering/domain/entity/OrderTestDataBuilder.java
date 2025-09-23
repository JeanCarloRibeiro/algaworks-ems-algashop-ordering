package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.enums.OrderStatus;
import com.algashop.ordering.domain.enums.PaymentMethod;
import com.algashop.ordering.domain.valueobject.Address;
import com.algashop.ordering.domain.valueobject.BillingInfo;
import com.algashop.ordering.domain.valueobject.Document;
import com.algashop.ordering.domain.valueobject.FullName;
import com.algashop.ordering.domain.valueobject.Money;
import com.algashop.ordering.domain.valueobject.Phone;
import com.algashop.ordering.domain.valueobject.ProductName;
import com.algashop.ordering.domain.valueobject.Quantity;
import com.algashop.ordering.domain.valueobject.ShippingInfo;
import com.algashop.ordering.domain.valueobject.Zipcode;
import com.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algashop.ordering.domain.valueobject.id.ProductId;

import java.time.LocalDate;

public class OrderTestDataBuilder {

  private CustomerId customerId = new CustomerId();
  private PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
  private Money shippingCost = new Money("10");
  private LocalDate expectedDeliveryDate = LocalDate.now().plusWeeks(1);
  private ShippingInfo shippingInfo = shippingInfo();
  private BillingInfo billingInfo = billingInfo();

  private boolean withItems = true;
  private OrderStatus orderStatus = OrderStatus.DRAFT;

  private OrderTestDataBuilder() {
  }

  public static OrderTestDataBuilder Order() {
    return new OrderTestDataBuilder();
  }

  public Order build() {
    Order order = Order.draft(customerId);
    order.changeShipping(shippingInfo, shippingCost, expectedDeliveryDate);
    order.changeBilling(billingInfo);
    order.changePaymentMethod(paymentMethod);

    if (withItems) {
      order.addItem(new ProductId(), new ProductName("Notebook X11"),
              new Money("3000"), new Quantity(2));

      order.addItem(new ProductId(), new ProductName("Memory 16GB RAM"),
              new Money("400"), new Quantity(1));
    }

    switch (this.orderStatus) {
      case DRAFT -> {
        // TODO Implement
      }
      case PLACED -> {
        order.place();
      }
      case PAID -> {
        order.place();
        order.paid();
      }
      case READY -> {
        // TODO Implement
      }
      case CANCELED -> {
        // TODO Implement
      }

    }
    return order;
  }

  public static ShippingInfo shippingInfo() {
    return ShippingInfo.builder()
            .address(address())
            .fullName(new FullName("Jean Carlo", "Ribeiro"))
            .document(new Document("255-08-0578"))
            .phone(new Phone("478-256-2504"))
            .build();
  }

  public static BillingInfo billingInfo() {
    return BillingInfo.builder()
            .address(address())
            .document(new Document("255-08-0578"))
            .fullName(new FullName("Jean Carlo", "Ribeiro"))
            .phone(new Phone("478-256-2504"))
            .build();
  }

  public static Address address() {
    return Address.builder()
            .street("Bourbon Street")
            .number("1234")
            .neighborhood("North Ville")
            .city("Montfort")
            .state("South Carolina")
            .zipcode(new Zipcode("79911"))
            .complement("House. 1")
            .build();
  }

  public OrderTestDataBuilder customerId(CustomerId customerId) {
    this.customerId = customerId;
    return this;
  }

  public OrderTestDataBuilder paymentMethod(PaymentMethod paymentMethod) {
    this.paymentMethod = paymentMethod;
    return this;
  }

  public OrderTestDataBuilder shippingCost(Money shippingCost) {
    this.shippingCost = shippingCost;
    return this;
  }

  public OrderTestDataBuilder expectedDeliveryDate(LocalDate expectedDeliveryDate) {
    this.expectedDeliveryDate = expectedDeliveryDate;
    return this;
  }

  public OrderTestDataBuilder shippingInfo(ShippingInfo shippingInfo) {
    this.shippingInfo = shippingInfo;
    return this;
  }

  public OrderTestDataBuilder billingInfo(BillingInfo billingInfo) {
    this.billingInfo = billingInfo;
    return this;
  }

  public OrderTestDataBuilder withItems(boolean withItems) {
    this.withItems = withItems;
    return this;
  }

  public OrderTestDataBuilder orderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
    return this;
  }
}
