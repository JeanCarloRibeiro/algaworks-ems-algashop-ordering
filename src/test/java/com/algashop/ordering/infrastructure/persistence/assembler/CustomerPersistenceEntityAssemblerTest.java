package com.algashop.ordering.infrastructure.persistence.assembler;

import com.algashop.ordering.domain.model.entity.Customer;
import com.algashop.ordering.domain.model.entity.databuilder.CustomerTestDataBuilder;
import com.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerPersistenceEntityAssemblerTest {

  private final CustomerPersistenceEntityAssembler assembler = new CustomerPersistenceEntityAssembler();

  @Test
  void shouldConvertToDomain() {
    Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
    CustomerPersistenceEntity persistenceEntity = assembler.fromDomain(customer);

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
            p -> Assertions.assertThat(p.getLoyaltyPoints()).isEqualTo(customer.loyaltyPoints().value())
    );

  }
}