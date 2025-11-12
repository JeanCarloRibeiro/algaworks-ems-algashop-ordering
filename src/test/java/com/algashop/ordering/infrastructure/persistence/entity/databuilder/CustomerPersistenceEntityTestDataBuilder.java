package com.algashop.ordering.infrastructure.persistence.entity.databuilder;

import com.algashop.ordering.domain.model.entity.databuilder.CustomerTestDataBuilder;
import com.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity.CustomerPersistenceEntityBuilder;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomerPersistenceEntityTestDataBuilder {

  private CustomerPersistenceEntityTestDataBuilder() {
  }

  public static CustomerPersistenceEntityBuilder existingCustomer() {
    return CustomerPersistenceEntity.builder()
            .id(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID.value())
            .firstName("Jean Carlo")
            .lastName("Ribeiro")
            .birthDate(LocalDate.of(1986, 2, 10))
            .email("jean@test.com")
            .phone("478-256-2504")
            .document("255-08-0578")
            .promotionNotificationsAllowed(false)
            .archived(false)
            .registeredAt(OffsetDateTime.now())
            .archivedAt(null)
            .loyaltyPoints(100)
            .address(AddressEmbeddable.builder()
                    .street("Bourbon Street")
                    .number("123")
                    .complement(null)
                    .neighborhood("North Ville")
                    .city("New York")
                    .state("South California")
                    .zipCode("12345")
                    .build());
  }

}
