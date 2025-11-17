package com.algashop.ordering.infrastructure.persistence.assembler;

import com.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algashop.ordering.domain.model.entity.databuilder.ShoppingCartTestDataBuilder;
import com.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ShoppingCartPersistenceEntityAssemblerTest {

  private final ShoppingCartPersistenceEntityAssembler assembler = new ShoppingCartPersistenceEntityAssembler();

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