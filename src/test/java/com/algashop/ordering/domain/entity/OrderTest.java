package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.enums.OrderStatus;
import com.algashop.ordering.domain.enums.PaymentMethod;
import com.algashop.ordering.domain.exception.OrderDoesNotContainOrderItemException;
import com.algashop.ordering.domain.exception.OrderInvalidShippingDeliveryDateException;
import com.algashop.ordering.domain.exception.OrderStatusCannotBeChangedException;
import com.algashop.ordering.domain.valueobject.Address;
import com.algashop.ordering.domain.valueobject.Billing;
import com.algashop.ordering.domain.valueobject.Document;
import com.algashop.ordering.domain.valueobject.FullName;
import com.algashop.ordering.domain.valueobject.Money;
import com.algashop.ordering.domain.valueobject.Phone;
import com.algashop.ordering.domain.valueobject.Product;
import com.algashop.ordering.domain.valueobject.ProductName;
import com.algashop.ordering.domain.valueobject.Quantity;
import com.algashop.ordering.domain.valueobject.Shipping;
import com.algashop.ordering.domain.valueobject.Zipcode;
import com.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algashop.ordering.domain.valueobject.id.OrderItemId;
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
    Product product = ProductTestDataBuilder.productAltMousePad().build();
    ProductId productId = product.id();

    order.addItem(product, new Quantity(1));

    Assertions.assertThat(order.items()).isNotEmpty();
    Assertions.assertThat(order.items()).hasSize(1);

    OrderItem orderItem = order.items().iterator().next();

    Assertions.assertWith(orderItem,
            i -> Assertions.assertThat(i.id()).isNotNull(),
            i -> Assertions.assertThat(i.productId()).isEqualTo(productId),
            i -> Assertions.assertThat(i.productName()).isEqualTo(new ProductName("Mouse Pad")),
            i -> Assertions.assertThat(i.price()).isEqualTo(new Money("100")),
            i -> Assertions.assertThat(i.quantity()).isEqualTo(new Quantity(1))
    );

  }

  @Test
  void shouldGenerateExceptionWhenTryToChangeItemSet() {
    Order order = Order.draft(new CustomerId());
    Product product = ProductTestDataBuilder.productAltMousePad().build();

    order.addItem(product, new Quantity(1));
    Set<OrderItem> items = order.items();

    Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
            .isThrownBy(items::clear);
  }

  @Test
  void shouldCalculateTotals() {
    Order order = Order.draft(new CustomerId());
    Product product = ProductTestDataBuilder.productAltMousePad().build();
    order.addItem(product, new Quantity(1));

    Product memory = ProductTestDataBuilder.productAltRamMemory().build();
    order.addItem(memory, new Quantity(1));
    Assertions.assertWith(order,
            i -> Assertions.assertThat(i.totalAmount()).isEqualTo(new Money("1100")),
            i -> Assertions.assertThat(i.totalItems()).isEqualTo(new Quantity(2))
    );

  }

  @Test
  void givenDraftOrderWhenPlaceShouldChanceToPlaced() {
    Order order = OrderTestDataBuilder.Order().build();
    order.place();
    Assertions.assertThat(order.isPlaced()).isTrue();
  }

  @Test
  void givenPlacedOrderWhenTryToPlaceGenerateException() {
    Order order = OrderTestDataBuilder.Order().orderStatus(OrderStatus.PLACED).build();

    Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
            .isThrownBy(order::place);
  }

  @Test
  void givenPlacedOrderWhenPaidShouldChangeToPaid() {
    Order order = OrderTestDataBuilder.Order().orderStatus(OrderStatus.PLACED).build();
    order.paid();
    Assertions.assertThat(order.status()).isEqualTo(OrderStatus.PAID);
    Assertions.assertThat(order.paidAt()).isNotNull();
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

    Billing billing = Billing.builder()
            .address(address)
            .document(new Document("255-08-0578"))
            .fullName(new FullName("Jean Carlo", "Ribeiro"))
            .phone(new Phone("478-256-2504"))
            .build();

    Order order = Order.draft(new CustomerId());
    order.changeBilling(billing);

    Billing expectedBilling = Billing.builder()
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

    Shipping shipping = Shipping.builder()
            .cost(new Money("10"))
            .expectedDate(LocalDate.now().plusWeeks(1))
            //.address(address)
            //.fullName(new FullName("Jean Carlo", "Ribeiro"))
            //.document(new Document("255-08-0578"))
            //.phone(new Phone("478-256-2504"))
            .build();

    Order order = Order.draft(new CustomerId());
    order.changeShipping(shipping);

    Shipping expectedShipping = Shipping.builder()
            .cost(new Money("10"))
            .expectedDate(LocalDate.now().plusWeeks(1))
            .build();

    Assertions.assertWith(order, o -> Assertions.assertThat(o.shipping()).isEqualTo(expectedShipping));

  }

  @Test
  void givenDraftOrderAndDeliveryDateInThePastWhenChangeShippingInfoShouldNotAllowChange2() {
    Order order = Order.draft(new CustomerId());

    Shipping shipping = Shipping.builder()
            .cost(new Money("10"))
            .expectedDate(LocalDate.now().minusWeeks(1))
            .build();

    Assertions.assertThatExceptionOfType(OrderInvalidShippingDeliveryDateException.class)
            .isThrownBy(() -> order.changeShipping(shipping));
  }

  @Test
  void givenDraftOrderWhenChangeItemQuantityShouldRecalculate() {
    Order order = Order.draft(new CustomerId());

    Product product = ProductTestDataBuilder.productAltMousePad().build();
    order.addItem(product, new Quantity(1));

    OrderItem orderItemId = order.items().iterator().next();
    order.changeItemQuantity(orderItemId.id(), new Quantity(5));

    Assertions.assertWith(order,
            o -> Assertions.assertThat(order.totalAmount()).isEqualTo(new Money("500")),
            o -> Assertions.assertThat(order.totalItems()).isEqualTo(new Quantity(5))
    );

  }

  @Test
  void givenDraftOrderWhenChangeItemQuantityWithItemNotExistShouldThrowException() {
    Order order = Order.draft(new CustomerId());

    Product product = ProductTestDataBuilder.product().build();
    order.addItem(product, new Quantity(1));

    Assertions.assertThatExceptionOfType(OrderDoesNotContainOrderItemException.class)
            .isThrownBy(() -> order.changeItemQuantity(new OrderItemId(), new Quantity(5)));
  }

}