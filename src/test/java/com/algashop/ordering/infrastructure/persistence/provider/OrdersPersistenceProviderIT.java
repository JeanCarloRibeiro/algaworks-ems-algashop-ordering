package com.algashop.ordering.infrastructure.persistence.provider;

import com.algashop.ordering.domain.model.entity.Order;
import com.algashop.ordering.domain.model.entity.databuilder.CustomerTestDataBuilder;
import com.algashop.ordering.domain.model.entity.databuilder.OrderTestDataBuilder;
import com.algashop.ordering.domain.model.enums.OrderStatus;
import com.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({
        OrdersPersistenceProvider.class,
        OrderPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class,
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class,
        SpringDataAuditingConfig.class
})
class OrdersPersistenceProviderIT {

  private final OrdersPersistenceProvider persistenceProvider;
  private final OrderPersistenceEntityRepository entityRepository;
  private final CustomersPersistenceProvider customersPersistenceProvider;

  @Autowired
  public OrdersPersistenceProviderIT(OrdersPersistenceProvider persistenceProvider, OrderPersistenceEntityRepository entityRepository, CustomersPersistenceProvider customersPersistenceProvider) {
    this.persistenceProvider = persistenceProvider;
    this.entityRepository = entityRepository;
    this.customersPersistenceProvider = customersPersistenceProvider;
  }

  @BeforeEach
  void setUp() {
    if (!customersPersistenceProvider.exists(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)) {
      customersPersistenceProvider.add(CustomerTestDataBuilder.existingCustomer().build());
    }

  }

  @Test
  void shouldUpdateAndKeepPersistenceEntityState() {
    Order order = OrderTestDataBuilder.Order().orderStatus(OrderStatus.PLACED).build();
    long orderId = order.id().value().toLong();
    persistenceProvider.add(order);

    OrderPersistenceEntity persistenceEntity = entityRepository.findById(orderId).orElseThrow();

    Assertions.assertThat(persistenceEntity.getStatus()).isEqualTo(OrderStatus.PLACED.name());

    Assertions.assertThat(persistenceEntity.getCreatedByUserId()).isNotNull();
    Assertions.assertThat(persistenceEntity.getLastModifiedAt()).isNotNull();
    Assertions.assertThat(persistenceEntity.getLastModifiedByUserId()).isNotNull();

    order = persistenceProvider.ofId(order.id()).orElseThrow();
    order.markAsPaid();
    persistenceProvider.add(order);

    persistenceEntity = entityRepository.findById(orderId).orElseThrow();

    Assertions.assertThat(persistenceEntity.getStatus()).isEqualTo(OrderStatus.PAID.name());

    Assertions.assertThat(persistenceEntity.getCreatedByUserId()).isNotNull();
    Assertions.assertThat(persistenceEntity.getLastModifiedAt()).isNotNull();
    Assertions.assertThat(persistenceEntity.getLastModifiedByUserId()).isNotNull();

  }
}