package com.algashop.ordering.infrastructure.persistence.repository;

import com.algashop.ordering.domain.model.utility.IdGenerator;
import com.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.databuilder.OrderPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
  }

  @Test
  void shouldCount() {
    long count = orderPersistenceEntityRepository.count();
    Assertions.assertThat(count).isZero();
  }

}