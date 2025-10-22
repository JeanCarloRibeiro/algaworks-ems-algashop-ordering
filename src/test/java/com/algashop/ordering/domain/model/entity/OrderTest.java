package com.algashop.ordering.domain.model.entity;

import com.algashop.ordering.domain.model.entity.databuilder.OrderTestDataBuilder;
import com.algashop.ordering.domain.model.entity.databuilder.ProductTestDataBuilder;
import com.algashop.ordering.domain.model.enums.OrderStatus;
import com.algashop.ordering.domain.model.enums.PaymentMethod;
import com.algashop.ordering.domain.model.exception.OrderDoesNotContainOrderItemException;
import com.algashop.ordering.domain.model.exception.OrderInvalidShippingDeliveryDateException;
import com.algashop.ordering.domain.model.exception.OrderStatusCannotBeChangedException;
import com.algashop.ordering.domain.model.exception.ProductOutOfStockException;
import com.algashop.ordering.domain.model.valueobject.Address;
import com.algashop.ordering.domain.model.valueobject.Billing;
import com.algashop.ordering.domain.model.valueobject.Document;
import com.algashop.ordering.domain.model.valueobject.Email;
import com.algashop.ordering.domain.model.valueobject.FullName;
import com.algashop.ordering.domain.model.valueobject.Money;
import com.algashop.ordering.domain.model.valueobject.Phone;
import com.algashop.ordering.domain.model.valueobject.Product;
import com.algashop.ordering.domain.model.valueobject.ProductName;
import com.algashop.ordering.domain.model.valueobject.Quantity;
import com.algashop.ordering.domain.model.valueobject.Shipping;
import com.algashop.ordering.domain.model.valueobject.ZipCode;
import com.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algashop.ordering.domain.model.valueobject.id.OrderItemId;
import com.algashop.ordering.domain.model.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

class OrderTest {

  @Test
  void shouldGenerateDraftOrder() {
    CustomerId customerId = new CustomerId();
    Order order = Order.draft(customerId);

    Assertions.assertWith(order,
            o -> Assertions.assertThat(order.id()).isNotNull(),
            o -> Assertions.assertThat(order.customerId()).isEqualTo(customerId),
            o -> Assertions.assertThat(order.totalAmount()).isEqualTo(Money.ZERO),
            o -> Assertions.assertThat(order.totalItems()).isEqualTo(Quantity.ZERO),
            o -> Assertions.assertThat(order.isDraft()).isTrue(),
            o -> Assertions.assertThat(order.items()).isEmpty(),

            o -> Assertions.assertThat(order.placedAt()).isNull(),
            o -> Assertions.assertThat(order.paidAt()).isNull(),
            o -> Assertions.assertThat(order.canceledAt()).isNull(),
            o -> Assertions.assertThat(order.readyAt()).isNull(),
            o -> Assertions.assertThat(order.billing()).isNull(),
            o -> Assertions.assertThat(order.shipping()).isNull(),
            o -> Assertions.assertThat(order.status()).isEqualTo(OrderStatus.DRAFT),
            o -> Assertions.assertThat(order.paymentMethod()).isNull()
    );
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
  void givenDraftOrderWhenPlaceShouldChangeToPlaced() {
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
    order.markAsPaid();
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
  void givenDraftOrderWhenChangeBillingShouldAllowChange() {
    Address address = Address.builder()
            .street("Bourbon Street")
            .number("1234")
            .neighborhood("North Ville")
            .city("Montfort")
            .state("South Carolina")
            .zipCode(new ZipCode("79911"))
            .complement("House. 1")
            .build();

    Billing billing = Billing.builder()
            .address(address)
            .document(new Document("255-08-0578"))
            .fullName(new FullName("Jean Carlo", "Ribeiro"))
            .phone(new Phone("478-256-2504"))
            .email(new Email("jean.ribeiro@gmail.com"))
            .build();

    Order order = Order.draft(new CustomerId());
    order.changeBilling(billing);

    Billing expectedBilling = Billing.builder()
            .address(address)
            .document(new Document("255-08-0578"))
            .fullName(new FullName("Jean Carlo", "Ribeiro"))
            .phone(new Phone("478-256-2504"))
            .email(new Email("jean.ribeiro@gmail.com"))
            .build();

    Assertions.assertThat(order.billing()).isEqualTo(expectedBilling);
  }

  @Test
  void givenDraftOrderWhenChangeShippingShouldAllowChange() {
    Shipping shipping = OrderTestDataBuilder.shipping();

    Order order = Order.draft(new CustomerId());
    order.changeShipping(shipping);

    Assertions.assertWith(order, o -> Assertions.assertThat(o.shipping()).isEqualTo(shipping));

  }

  @Test
  void givenDraftOrderAndDeliveryDateInThePastWhenChangeShippingShouldNotAllowChange2() {
    Order order = Order.draft(new CustomerId());

    LocalDate expectedDate = LocalDate.now().minusWeeks(1);

    Shipping shipping = OrderTestDataBuilder.shipping().toBuilder()
            .expectedDate(expectedDate)
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

  @Test
  void givenProductOutOfStockWhenTryToAddAnOrderShoudReturnNotAllow() {
    Order order = Order.draft(new CustomerId());

    Product product = ProductTestDataBuilder.productUnavailable().build();

    ThrowableAssert.ThrowingCallable callable = () -> order.addItem(product, new Quantity(1));
    Assertions.assertThatExceptionOfType(ProductOutOfStockException.class).isThrownBy(callable);

  }

}