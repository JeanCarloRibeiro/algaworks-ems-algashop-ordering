package com.algashop.ordering.infrastructure.persistence.assembler;

import com.algashop.ordering.domain.model.entity.Order;
import com.algashop.ordering.domain.model.entity.databuilder.OrderTestDataBuilder;
import com.algashop.ordering.domain.model.valueobject.id.OrderItemId;
import com.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.databuilder.OrderPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class OrderPersistenceEntityAssemblerTest {

  private final OrderPersistenceEntityAssembler assembler = new OrderPersistenceEntityAssembler();

  @Test
  void shouldConvertToDomain() {
    Order order = OrderTestDataBuilder.Order().build();
    OrderPersistenceEntity orderPersistenceEntity = assembler.fromDomain(order);

    Assertions.assertThat(orderPersistenceEntity).satisfies(
            p -> Assertions.assertThat(p.getId()).isEqualTo(order.id().value().toLong()),
            p -> Assertions.assertThat(p.getCustomerId()).isEqualTo(order.customerId().value()),
            p -> Assertions.assertThat(p.getTotalAmount()).isEqualTo(order.totalAmount().value()),
            p -> Assertions.assertThat(p.getTotalItems()).isEqualTo(order.totalItems().value()),
            p -> Assertions.assertThat(p.getStatus()).isEqualTo(order.status().name()),
            p -> Assertions.assertThat(p.getPaymentMethod()).isEqualTo(order.paymentMethod().name()),
            p -> Assertions.assertThat(p.getPlacedAt()).isEqualTo(order.placedAt()),
            p -> Assertions.assertThat(p.getPaidAt()).isEqualTo(order.paidAt()),
            p -> Assertions.assertThat(p.getCanceledAt()).isEqualTo(order.canceledAt()),
            p -> Assertions.assertThat(p.getReadAt()).isEqualTo(order.readyAt())
    );
  }

  @Test
  void givenOrderWithNotItemsShouldRemovePersistenceEntityItems() {
    Order order = OrderTestDataBuilder.Order().withItems(false).build();
    OrderPersistenceEntity orderPersistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();

    Assertions.assertThat(order.items()).isEmpty();
    Assertions.assertThat(orderPersistenceEntity.getItems()).isNotEmpty();

    assembler.merge(orderPersistenceEntity, order);
    Assertions.assertThat(orderPersistenceEntity.getItems()).isEmpty();
  }

  @Test
  void givenOrderWithItemsShouldAddToPersistenceEntity() {
    Order order = OrderTestDataBuilder.Order().withItems(true).build();
    OrderPersistenceEntity orderPersistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder()
            .items(new HashSet<>()).build();

    Assertions.assertThat(order.items()).isNotEmpty();
    Assertions.assertThat(orderPersistenceEntity.getItems()).isEmpty();

    assembler.merge(orderPersistenceEntity, order);
    Assertions.assertThat(orderPersistenceEntity.getItems()).isNotEmpty();
    Assertions.assertThat(orderPersistenceEntity.getItems().size()).isEqualTo(order.items().size());
  }

  @Test
  void givenOrderWithItemsWhenMergeShouldRemoveMerge() {
    Order order = OrderTestDataBuilder.Order().withItems(true).build();

    Assertions.assertThat(order.items().size()).isEqualTo(2);

    Set<OrderItemPersistenceEntity> orderItemPersistenceEntities = order.items().stream()
            .map(assembler::fromDomain).collect(Collectors.toSet());

    OrderPersistenceEntity orderPersistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder()
            .items(orderItemPersistenceEntities).build();

    OrderItemId orderItemId = order.items().iterator().next().id();
    order.removeItem(orderItemId);

    assembler.merge(orderPersistenceEntity, order);

    Assertions.assertThat(order.items()).isNotEmpty();
    Assertions.assertThat(orderPersistenceEntity.getItems()).isNotEmpty();
    Assertions.assertThat(orderPersistenceEntity.getItems().size()).isEqualTo(order.items().size());
  }

}