package com.algashop.ordering.infrastructure.persistence.disassembler;

import com.algashop.ordering.domain.model.entity.Customer;
import com.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.databuilder.CustomerPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerPersistenceEntityDisassemblerTest {

  private final CustomerPersistenceEntityDisassembler disassembler = new CustomerPersistenceEntityDisassembler();

  @Test
  void shouldConvertToDomain() {
    CustomerPersistenceEntity persistenceEntity = CustomerPersistenceEntityTestDataBuilder.existingCustomer().build();
    Customer customer = disassembler.toDomainEntity(persistenceEntity);

    Assertions.assertThat(persistenceEntity).satisfies(
            p -> Assertions.assertThat(p.getId()).isEqualTo(customer.id().value()),
            p -> Assertions.assertThat(p.getFirstName()).isEqualTo(customer.fullName().firstName()),
            p -> Assertions.assertThat(p.getLastName()).isEqualTo(customer.fullName().lastName()),
            p -> Assertions.assertThat(p.getBirthDate()).isEqualTo(customer.birthDate().value()),
            p -> Assertions.assertThat(p.getEmail()).isEqualTo(customer.email().value()),
            p -> Assertions.assertThat(p.getPhone()).isEqualTo(customer.phone().value()),
            p -> Assertions.assertThat(p.getDocument()).isEqualTo(customer.document().value()),
            p -> Assertions.assertThat(p.getPromotionNotificationsAllowed()).isEqualTo(customer.isPromotionNotificationsAllowed()),
            p -> Assertions.assertThat(p.getArchived()).isEqualTo(customer.isArchived()),
            p -> Assertions.assertThat(p.getArchivedAt()).isEqualTo(customer.archivedAt()),
            p -> Assertions.assertThat(p.getRegisteredAt()).isEqualTo(customer.registeredAt()),
            p -> Assertions.assertThat(p.getLoyaltyPoints()).isEqualTo(customer.loyaltyPoints().value())
    );

  }
}