package com.algashop.ordering.domain.model.repository;

import com.algashop.ordering.domain.model.entity.Order;
import com.algashop.ordering.domain.model.entity.databuilder.OrderTestDataBuilder;
import com.algashop.ordering.domain.model.enums.OrderStatus;
import com.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.algashop.ordering.infrastructure.persistence.provider.OrdersPersistenceProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({OrdersPersistenceProvider.class, OrderPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class
})
class OrdersIT {

  private final Orders orders;

  @Autowired
  public OrdersIT(Orders orders) {
    this.orders = orders;
  }

  @Test
  void shouldPersistAndFind() {
    Order order = OrderTestDataBuilder.Order().build();
    OrderId orderId = order.id();
    orders.add(order);

    Optional<Order> possibleOrder = orders.ofId(orderId);

    assertThat(possibleOrder).isPresent();

    Order savedOrder = possibleOrder.get();
    assertThat(savedOrder).satisfies(
            s -> assertThat(s.id()).isEqualTo(order.id()),
            s -> assertThat(s.customerId()).isEqualTo(order.customerId()),
            s -> assertThat(s.totalAmount()).isEqualTo(order.totalAmount()),
            s -> assertThat(s.totalItems()).isEqualTo(order.totalItems()),
            s -> assertThat(s.placedAt()).isEqualTo(order.placedAt()),
            s -> assertThat(s.paidAt()).isEqualTo(order.paidAt()),
            s -> assertThat(s.canceledAt()).isEqualTo(order.canceledAt()),
            s -> assertThat(s.readyAt()).isEqualTo(order.readyAt()),
            s -> assertThat(s.status()).isEqualTo(order.status()),
            s -> assertThat(s.paymentMethod()).isEqualTo(order.paymentMethod())

    );
  }

  @Test
  void shouldUpdateExistingOrder() {
    Order order = OrderTestDataBuilder.Order().orderStatus(OrderStatus.PLACED).build();
    orders.add(order);

    order = orders.ofId(order.id()).orElseThrow();
    order.markAsPaid();

    orders.add(order);
    order = orders.ofId(order.id()).orElseThrow();

    Assertions.assertThat(order.isPaid()).isTrue();
  }

  @Test
  void shouldNotAllowedStaleUpdates() {
    Order order = OrderTestDataBuilder.Order().orderStatus(OrderStatus.PLACED).build();
    orders.add(order);

    Order orderT1 = orders.ofId(order.id()).orElseThrow();
    Order orderT2 = orders.ofId(order.id()).orElseThrow();

    orderT1.markAsPaid();
    orders.add(orderT1);

    orderT2.cancel();

    Assertions.assertThatExceptionOfType(ObjectOptimisticLockingFailureException.class)
            .isThrownBy(() -> orders.add(orderT2));
  }

}