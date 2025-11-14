package com.algashop.ordering.infrastructure.persistence.assembler;

import com.algashop.ordering.domain.model.entity.Customer;
import com.algashop.ordering.domain.model.valueobject.Address;
import com.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerPersistenceEntityAssembler {

  public CustomerPersistenceEntity fromDomain(Customer customer) {
    return merge(new CustomerPersistenceEntity(), customer);
  }

  public CustomerPersistenceEntity merge(CustomerPersistenceEntity entity, Customer customer) {
    entity.setId(customer.id().value());
    entity.setFirstName(customer.fullName().firstName());
    entity.setLastName(customer.fullName().lastName());
    entity.setBirthDate(customer.birthDate() != null ? customer.birthDate().value() : null);
    entity.setEmail(customer.email().value());
    entity.setPhone(customer.phone().value());
    entity.setDocument(customer.document().value());
    entity.setPromotionNotificationsAllowed(customer.isPromotionNotificationsAllowed());
    entity.setArchived(customer.isArchived());
    entity.setRegisteredAt(customer.registeredAt());
    entity.setArchivedAt(customer.archivedAt());
    entity.setLoyaltyPoints(customer.loyaltyPoints().value());
    entity.setAddress(toAddressEmbeddable(customer.address()));
    entity.setVersion(customer.version());

    return entity;
  }

  private AddressEmbeddable toAddressEmbeddable(Address address) {
    return AddressEmbeddable.builder()
            .street(address.street())
            .number(address.number())
            .complement(address.complement())
            .neighborhood(address.neighborhood())
            .city(address.city())
            .state(address.state())
            .zipCode(address.zipCode().value())
            .build();
  }

}
