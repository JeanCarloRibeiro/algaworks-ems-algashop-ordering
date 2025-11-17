package com.algashop.ordering.infrastructure.persistence.disassembler;

import com.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.databuilder.ShoppingCartPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ShoppingCartPersistenceEntityDisassemblerTest {

  private final ShoppingCartPersistenceEntityDisassembler disassembler = new ShoppingCartPersistenceEntityDisassembler();

  @Test
  void shouldConvertToDomain() {
    ShoppingCartPersistenceEntity persistenceEntity = ShoppingCartPersistenceEntityTestDataBuilder.existingShopping().build();
    ShoppingCart shoppingCart = disassembler.toDomainEntity(persistenceEntity);

    Assertions.assertThat(persistenceEntity).satisfies(
            p -> Assertions.assertThat(p.getId()).isEqualTo(shoppingCart.id().value()),
            p -> Assertions.assertThat(p.getCustomerId()).isEqualTo(shoppingCart.customerId().value()),
            p -> Assertions.assertThat(p.getTotalAmount()).isEqualTo(shoppingCart.totalAmount().value()),
            p -> Assertions.assertThat(p.getTotalItems()).isEqualTo(shoppingCart.totalItems().value()),
            p -> Assertions.assertThat(p.getCreatedAt()).isEqualTo(shoppingCart.createdAt())
    );

  }
}