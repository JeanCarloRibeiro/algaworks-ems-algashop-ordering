package com.algashop.ordering.infrastructure.persistence.provider;

import com.algashop.ordering.domain.model.entity.Customer;
import com.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algashop.ordering.domain.model.entity.databuilder.CustomerTestDataBuilder;
import com.algashop.ordering.domain.model.entity.databuilder.ShoppingCartTestDataBuilder;
import com.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algashop.ordering.infrastructure.persistence.assembler.ShoppingCartPersistenceEntityAssembler;
import com.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.algashop.ordering.infrastructure.persistence.disassembler.ShoppingCartPersistenceEntityDisassembler;
import com.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.repository.ShoppingCartPersistenceEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

@DataJpaTest
@Import({
        ShoppingCartsPersistenceProvider.class,
        ShoppingCartPersistenceEntityAssembler.class,
        ShoppingCartPersistenceEntityDisassembler.class,
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityDisassembler.class,
        CustomerPersistenceEntityAssembler.class,
        SpringDataAuditingConfig.class
})
class ShoppingCartsPersistenceProviderIT {

  private final ShoppingCartsPersistenceProvider persistenceProvider;
  private final ShoppingCartPersistenceEntityRepository entityRepository;
  private final CustomersPersistenceProvider customersPersistenceProvider;

  @Autowired
  public ShoppingCartsPersistenceProviderIT(ShoppingCartsPersistenceProvider persistenceProvider,
                                            ShoppingCartPersistenceEntityRepository entityRepository,
                                            CustomersPersistenceProvider customersPersistenceProvider) {
    this.persistenceProvider = persistenceProvider;
    this.entityRepository = entityRepository;
    this.customersPersistenceProvider = customersPersistenceProvider;
  }

  @Test
  void shouldUpdateAndKeepPersistenceEntityState() {
    Customer customer = CustomerTestDataBuilder.existingCustomer().build();
    customersPersistenceProvider.add(customer);

    ShoppingCart shopping = ShoppingCartTestDataBuilder.shoppingCart().build();
    UUID shoppingId = shopping.id().value();
    persistenceProvider.add(shopping);

    ShoppingCartPersistenceEntity persistenceEntity = entityRepository.findById(shoppingId).orElseThrow();

    Assertions.assertThat(persistenceEntity.getCreatedByUserId()).isNotNull();
    Assertions.assertThat(persistenceEntity.getLastModifiedAt()).isNotNull();
    Assertions.assertThat(persistenceEntity.getLastModifiedByUserId()).isNotNull();

  }

  @Test
  void shouldReturnByCustomer() {

    Customer customer = CustomerTestDataBuilder.existingCustomer().build();
    customersPersistenceProvider.add(customer);

    ShoppingCart shopping = ShoppingCartTestDataBuilder.shoppingCart().build();
    persistenceProvider.add(shopping);

    ShoppingCart shoppingCart = persistenceProvider.ofCustomer(shopping.customerId()).orElseThrow();

    Assertions.assertThat(shoppingCart.id()).isNotNull();
    Assertions.assertThat(shoppingCart.createdAt()).isNotNull();
    Assertions.assertThat(shoppingCart.customerId()).isNotNull();
  }

}