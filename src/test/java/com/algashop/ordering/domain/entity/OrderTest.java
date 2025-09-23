package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.enums.PaymentMethod;
import com.algashop.ordering.domain.exception.OrderInvalidShippingDeliveryDateException;
import com.algashop.ordering.domain.exception.OrderStatusCannotBeChangedException;
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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

class OrderTest {

  @Test
  void shouldGenerate() {
    Order order = Order.draft(new CustomerId());
    Assertions.assertThat(order.customerId()).isNotNull();
  }

  @Test
  void shouldAddItem() {
    Order order = Order.draft(new CustomerId());
    ProductId productId = new ProductId();

    order.addItem(
            productId,
            new ProductName("Mouse pad"),
            new Money("100"),
            new Quantity(1)
    );
    Assertions.assertThat(order.items()).isNotEmpty();
    Assertions.assertThat(order.items()).hasSize(1);

    OrderItem orderItem = order.items().iterator().next();

    Assertions.assertWith(orderItem,
            i -> Assertions.assertThat(i.id()).isNotNull(),
            i -> Assertions.assertThat(i.productId()).isEqualTo(productId),
            i -> Assertions.assertThat(i.productName()).isEqualTo(new ProductName("Mouse pad")),
            i -> Assertions.assertThat(i.price()).isEqualTo(new Money("100")),
            i -> Assertions.assertThat(i.quantity()).isEqualTo(new Quantity(1))
    );

  }

  @Test
  void shouldGenerateExceptionWhenTryToChangeItemSet() {
    Order order = Order.draft(new CustomerId());
    ProductId productId = new ProductId();

    order.addItem(
            productId,
            new ProductName("Mouse pad"),
            new Money("100"),
            new Quantity(1)
    );

    Set<OrderItem> items = order.items();

    Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
            .isThrownBy(items::clear);
  }

  @Test
  void shouldCalculateTotals() {
    Order order = Order.draft(new CustomerId());
    order.addItem(
            new ProductId(),
            new ProductName("Mouse pad"),
            new Money("100"),
            new Quantity(1)
    );
    order.addItem(
            new ProductId(),
            new ProductName("Monitor"),
            new Money("1000"),
            new Quantity(1)
    );
    Assertions.assertWith(order,
            i -> Assertions.assertThat(i.totalAmount()).isEqualTo(new Money("1100")),
            i -> Assertions.assertThat(i.totalItems()).isEqualTo(new Quantity(2))
    );

  }

  @Test
  void givenDraftOrderWhenPlaceShouldChanceToPlaced() {
    Order order = Order.draft(new CustomerId());
    order.place();
    Assertions.assertThat(order.isPlaced()).isTrue();
  }

  @Test
  void givenPlacedOrderWhenTryToPlaceGenerateException() {
    Order order = Order.draft(new CustomerId());
    order.place();
    Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
            .isThrownBy(order::place);
  }

  @Test
  void givenDraftOrderWhenChangePaymentMethodShouldAllowChange() {
    Order order = Order.draft(new CustomerId());
    order.changePaymentMethod(PaymentMethod.CREDIT_CARD);
    Assertions.assertThat(order.paymentMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
  }

  @Test
  void givenDraftOrderWhenChangeBillingInfoShouldAllowChange() {
    Address address = Address.builder()
            .street("Bourbon Street")
            .number("1234")
            .neighborhood("North Ville")
            .city("Montfort")
            .state("South Carolina")
            .zipcode(new Zipcode("79911"))
            .complement("House. 1")
            .build();

    BillingInfo billing = BillingInfo.builder()
            .address(address)
            .document(new Document("255-08-0578"))
            .fullName(new FullName("Jean Carlo", "Ribeiro"))
            .phone(new Phone("478-256-2504"))
            .build();

    Order order = Order.draft(new CustomerId());
    order.changeBilling(billing);

    BillingInfo expectedBilling = BillingInfo.builder()
            .address(address)
            .document(new Document("255-08-0578"))
            .fullName(new FullName("Jean Carlo", "Ribeiro"))
            .phone(new Phone("478-256-2504"))
            .build();

    Assertions.assertThat(order.billing()).isEqualTo(expectedBilling);
  }

  @Test
  void givenDraftOrderWhenChangeShippingInfoShouldAllowChange() {

    Address address = Address.builder()
            .street("Bourbon Street")
            .number("1234")
            .complement("House. 1")
            .neighborhood("North Ville")
            .city("Montfort")
            .state("South Carolina")
            .zipcode(new Zipcode("79911"))
            .build();

    ShippingInfo shipping = ShippingInfo.builder()
            .address(address)
            .fullName(new FullName("Jean Carlo", "Ribeiro"))
            .document(new Document("255-08-0578"))
            .phone(new Phone("478-256-2504"))
            .build();

    Money shippingCost = new Money("50");
    LocalDate expectedDeliveryDate = LocalDate.now().plusDays(1);

    Order order = Order.draft(new CustomerId());
    order.changeShipping(shipping, shippingCost, expectedDeliveryDate);

    ShippingInfo expectedShipping = ShippingInfo.builder()
            .address(address)
            .fullName(new FullName("Jean Carlo", "Ribeiro"))
            .document(new Document("255-08-0578"))
            .phone(new Phone("478-256-2504"))
            .build();

    Assertions.assertWith(order,
            o -> Assertions.assertThat(o.shipping()).isEqualTo(expectedShipping),
            o -> Assertions.assertThat(o.shippingCost()).isEqualTo(shippingCost),
            o -> Assertions.assertThat(o.expectedDeliveryDate()).isEqualTo(expectedDeliveryDate)
    );

  }

  @Test
  void givenDraftOrderAndDeliveryDateInThePastWhenChangeShippingInfoShouldNotAllowChange2() {

    Address address = Address.builder()
            .street("Bourbon Street")
            .number("1234")
            .complement("House. 1")
            .neighborhood("North Ville")
            .city("Montfort")
            .state("South Carolina")
            .zipcode(new Zipcode("79911"))
            .build();

    ShippingInfo shipping = ShippingInfo.builder()
            .address(address)
            .fullName(new FullName("Jean Carlo", "Ribeiro"))
            .document(new Document("255-08-0578"))
            .phone(new Phone("478-256-2504"))
            .build();

    Money shippingCost = new Money("50");
    LocalDate expectedDeliveryDate = LocalDate.now().minusDays(1);

    Order order = Order.draft(new CustomerId());

    Assertions.assertThatExceptionOfType(OrderInvalidShippingDeliveryDateException.class)
            .isThrownBy(() -> order.changeShipping(shipping, shippingCost, expectedDeliveryDate));
  }

}