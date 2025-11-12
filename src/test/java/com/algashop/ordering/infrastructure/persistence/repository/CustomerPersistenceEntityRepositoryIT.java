package com.algashop.ordering.infrastructure.persistence.repository;

import com.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.databuilder.CustomerPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(SpringDataAuditingConfig.class)
class CustomerPersistenceEntityRepositoryIT {

  private final CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

  @Autowired
  public CustomerPersistenceEntityRepositoryIT(CustomerPersistenceEntityRepository customerPersistenceEntityRepository) {
    this.customerPersistenceEntityRepository = customerPersistenceEntityRepository;
  }

  @Test
  void shouldPersist() {
    CustomerPersistenceEntity entity = CustomerPersistenceEntityTestDataBuilder.existingCustomer().build();

    customerPersistenceEntityRepository.saveAndFlush(entity);
    Assertions.assertThat(customerPersistenceEntityRepository.existsById(entity.getId())).isTrue();
  }

  @Test
  void shouldCount() {
    long count = customerPersistenceEntityRepository.count();
    Assertions.assertThat(count).isZero();
  }

  @Test
  void shouldSetAuditingValues() {
    CustomerPersistenceEntity entity = CustomerPersistenceEntityTestDataBuilder.existingCustomer().build();
    entity = customerPersistenceEntityRepository.saveAndFlush(entity);

    Assertions.assertThat(entity.getCreatedByUserId()).isNotNull();
    Assertions.assertThat(entity.getLastModifiedByUserId()).isNotNull();
    Assertions.assertThat(entity.getLastModifiedAt()).isNotNull();
  }

}