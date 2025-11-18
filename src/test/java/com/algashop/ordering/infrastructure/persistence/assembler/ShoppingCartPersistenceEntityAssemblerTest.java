package com.algashop.ordering.infrastructure.persistence.assembler;

import com.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algashop.ordering.domain.model.entity.databuilder.ShoppingCartTestDataBuilder;
import com.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.databuilder.CustomerPersistenceEntityTestDataBuilder;
import com.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingCartPersistenceEntityAssemblerTest {

  @Mock
  private CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

  @InjectMocks
  private ShoppingCartPersistenceEntityAssembler assembler;

  @BeforeEach
  void setUp() {
    when(customerPersistenceEntityRepository.getReferenceById(any()))
            .thenReturn(CustomerPersistenceEntityTestDataBuilder.existingCustomer().build());
  }

  @Test
  void shouldConvertToDomain() {
    ShoppingCart shopping = ShoppingCartTestDataBuilder.shoppingCart().build();
    ShoppingCartPersistenceEntity persistenceEntity = assembler.fromDomain(shopping);

    Assertions.assertThat(persistenceEntity).satisfies(
            p -> Assertions.assertThat(p.getId()).isEqualTo(shopping.id().value()),
            p -> Assertions.assertThat(p.getCustomerId()).isEqualTo(shopping.customerId().value()),
            p -> Assertions.assertThat(p.getTotalItems()).isEqualTo(shopping.totalItems().value()),
            p -> Assertions.assertThat(p.getTotalAmount()).isEqualTo(shopping.totalAmount().value()),
            p -> Assertions.assertThat(p.getCreatedAt()).isEqualTo(shopping.createdAt())
    );

  }
}