package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.enums.OrderStatus;
import com.algashop.ordering.domain.enums.PaymentMethod;
import com.algashop.ordering.domain.valueobject.Address;
import com.algashop.ordering.domain.valueobject.Billing;
import com.algashop.ordering.domain.valueobject.Document;
import com.algashop.ordering.domain.valueobject.Email;
import com.algashop.ordering.domain.valueobject.FullName;
import com.algashop.ordering.domain.valueobject.Money;
import com.algashop.ordering.domain.valueobject.Phone;
import com.algashop.ordering.domain.valueobject.Product;
import com.algashop.ordering.domain.valueobject.Quantity;
import com.algashop.ordering.domain.valueobject.Recipient;
import com.algashop.ordering.domain.valueobject.Shipping;
import com.algashop.ordering.domain.valueobject.Zipcode;
import com.algashop.ordering.domain.valueobject.id.CustomerId;

import java.time.LocalDate;

public class OrderTestDataBuilder {

  private CustomerId customerId = new CustomerId();
  private PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
  private Shipping shipping = shipping();
  private Billing billing = billing();

  private boolean withItems = true;
  private OrderStatus orderStatus = OrderStatus.DRAFT;

  private OrderTestDataBuilder() {
  }

  public static OrderTestDataBuilder Order() {
    return new OrderTestDataBuilder();
  }

  public Order build() {
    Order order = Order.draft(customerId);
    order.changeShipping(shipping);
    order.changeBilling(billing);
    order.changePaymentMethod(paymentMethod);

    if (withItems) {
      Product mouse = ProductTestDataBuilder.productAltMousePad().build();
      order.addItem(mouse, new Quantity(2));

      Product memory = ProductTestDataBuilder.productAltRamMemory().build();
      order.addItem(memory, new Quantity(1));
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
        order.markAsPaid();
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

  public static Shipping shipping() {
    return Shipping.builder()
            .cost(new Money("10"))
            .expectedDate(LocalDate.now().plusWeeks(1))
            .recipient(recipient())
            .address(address())
            .build();
  }

  public static Shipping shippingAlt() {
    return Shipping.builder()
            .cost(new Money("20"))
            .expectedDate(LocalDate.now().plusWeeks(2))
            .recipient(recipientAlt())
            .address(addressAlt())
            .build();
  }

  public static Billing billing() {
    return Billing.builder()
            .address(address())
            .document(new Document("255-08-0578"))
            .fullName(new FullName("Jean Carlo", "Ribeiro"))
            .phone(new Phone("478-256-2504"))
            .email(new Email("jean.ribeiro@gmail.com"))
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

  public static Address addressAlt() {
    return Address.builder()
            .street("Sansome Stree")
            .number("755")
            .neighborhood("Sansome")
            .city("Montfort")
            .state("South Carolina")
            .zipcode(new Zipcode("08040"))
            .build();
  }

  public static Recipient recipient() {
    return Recipient.builder()
            .document(new Document("255-08-0578"))
            .fullName(new FullName("Jean Carlo", "Ribeiro"))
            .phone(new Phone("478-256-2504"))
            .build();
  }

  public static Recipient recipientAlt() {
    return Recipient.builder()
            .document(new Document("255-08-0578"))
            .fullName(new FullName("Joao", "Silva"))
            .phone(new Phone("478-300-2101"))
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

  public OrderTestDataBuilder shipping(Shipping shipping) {
    this.shipping = shipping;
    return this;
  }

  public OrderTestDataBuilder billing(Billing billing) {
    this.billing = billing;
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
