package com.algashop.ordering.infrastructure.persistence.provider;

import com.algashop.ordering.domain.model.entity.Customer;
import com.algashop.ordering.domain.model.entity.databuilder.CustomerTestDataBuilder;
import com.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

@DataJpaTest
@Import({
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class,
        SpringDataAuditingConfig.class
})
class CustomersPersistenceProviderIT {

  private final CustomersPersistenceProvider persistenceProvider;
  private final CustomerPersistenceEntityRepository entityRepository;

  @Autowired
  public CustomersPersistenceProviderIT(CustomersPersistenceProvider persistenceProvider,
                                        CustomerPersistenceEntityRepository entityRepository) {
    this.persistenceProvider = persistenceProvider;
    this.entityRepository = entityRepository;
  }

  @Test
  void shouldUpdateAndKeepPersistenceEntityState() {
    Customer customer = CustomerTestDataBuilder.existingCustomer().build();
    UUID customerId = customer.id().value();
    persistenceProvider.add(customer);

    CustomerPersistenceEntity persistenceEntity = entityRepository.findById(customerId).orElseThrow();

    Assertions.assertThat(persistenceEntity.getCreatedByUserId()).isNotNull();
    Assertions.assertThat(persistenceEntity.getLastModifiedAt()).isNotNull();
    Assertions.assertThat(persistenceEntity.getLastModifiedByUserId()).isNotNull();

    customer = persistenceProvider.ofId(customer.id()).orElseThrow();
    customer.disablePromotionNotifications();
    persistenceProvider.add(customer);

    persistenceEntity = entityRepository.findById(customerId).orElseThrow();

    Assertions.assertThat(persistenceEntity.getCreatedByUserId()).isNotNull();
    Assertions.assertThat(persistenceEntity.getLastModifiedAt()).isNotNull();
    Assertions.assertThat(persistenceEntity.getLastModifiedByUserId()).isNotNull();

  }
}