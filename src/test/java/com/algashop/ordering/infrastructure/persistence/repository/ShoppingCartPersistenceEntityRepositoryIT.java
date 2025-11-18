package com.algashop.ordering.infrastructure.persistence.repository;

import com.algashop.ordering.domain.model.entity.databuilder.CustomerTestDataBuilder;
import com.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.databuilder.CustomerPersistenceEntityTestDataBuilder;
import com.algashop.ordering.infrastructure.persistence.entity.databuilder.ShoppingCartPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(SpringDataAuditingConfig.class)
class ShoppingCartPersistenceEntityRepositoryIT {

  private final ShoppingCartPersistenceEntityRepository persistenceEntityRepository;
  private final CustomerPersistenceEntityRepository customerPersistenceEntityRepository;
  private CustomerPersistenceEntity customerPersistenceEntity;

  @Autowired
  public ShoppingCartPersistenceEntityRepositoryIT(ShoppingCartPersistenceEntityRepository persistenceEntityRepository,
                                                   CustomerPersistenceEntityRepository customerPersistenceEntityRepository) {
    this.persistenceEntityRepository = persistenceEntityRepository;
    this.customerPersistenceEntityRepository = customerPersistenceEntityRepository;
  }

  @BeforeEach
  void setUp() {
    UUID customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID.value();
    if (!customerPersistenceEntityRepository.existsById(customerId)) {
      customerPersistenceEntity = customerPersistenceEntityRepository.saveAndFlush(
              CustomerPersistenceEntityTestDataBuilder.existingCustomer().build());
    }
  }

  @Test
  void shouldPersist() {
    ShoppingCartPersistenceEntity entity = ShoppingCartPersistenceEntityTestDataBuilder.existingShopping().build();
    entity.setCustomer(customerPersistenceEntity);

    persistenceEntityRepository.saveAndFlush(entity);
    Assertions.assertThat(persistenceEntityRepository.existsById(entity.getId())).isTrue();
  }

  @Test
  void shouldCount() {
    long count = persistenceEntityRepository.count();
    Assertions.assertThat(count).isZero();
  }

  @Test
  void shouldSetAuditingValues() {
    ShoppingCartPersistenceEntity entity = ShoppingCartPersistenceEntityTestDataBuilder.existingShopping().build();
    entity.setCustomer(customerPersistenceEntity);

    entity = persistenceEntityRepository.saveAndFlush(entity);

    Assertions.assertThat(entity.getCreatedByUserId()).isNotNull();
    Assertions.assertThat(entity.getLastModifiedByUserId()).isNotNull();
    Assertions.assertThat(entity.getLastModifiedAt()).isNotNull();
  }

}