package com.algashop.ordering.infrastructure.persistence.repository;

import com.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.databuilder.OrderPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(SpringDataAuditingConfig.class)
class OrderPersistenceEntityRepositoryIT {

  private final OrderPersistenceEntityRepository orderPersistenceEntityRepository;

  @Autowired
  public OrderPersistenceEntityRepositoryIT(OrderPersistenceEntityRepository orderPersistenceEntityRepository) {
    this.orderPersistenceEntityRepository = orderPersistenceEntityRepository;
  }

  @Test
  void shouldPersist() {
    OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();

    orderPersistenceEntityRepository.saveAndFlush(entity);
    Assertions.assertThat(orderPersistenceEntityRepository.existsById(entity.getId())).isTrue();

    OrderPersistenceEntity savedEntity = orderPersistenceEntityRepository.findById(entity.getId()).orElseThrow();
    Assertions.assertThat(savedEntity.getItems()).isNotEmpty();
  }

  @Test
  void shouldCount() {
    long count = orderPersistenceEntityRepository.count();
    Assertions.assertThat(count).isZero();
  }

  @Test
  void shouldSetAuditingValues() {
    OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();
    entity = orderPersistenceEntityRepository.saveAndFlush(entity);

    Assertions.assertThat(entity.getCreatedByUserId()).isNotNull();
    Assertions.assertThat(entity.getLastModifiedByUserId()).isNotNull();
    Assertions.assertThat(entity.getLastModifiedAt()).isNotNull();
  }

}