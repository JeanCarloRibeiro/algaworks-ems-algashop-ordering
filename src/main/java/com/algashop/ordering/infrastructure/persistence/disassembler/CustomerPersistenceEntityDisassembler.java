package com.algashop.ordering.infrastructure.persistence.disassembler;

import com.algashop.ordering.domain.model.entity.Customer;
import com.algashop.ordering.domain.model.valueobject.Address;
import com.algashop.ordering.domain.model.valueobject.BirthDate;
import com.algashop.ordering.domain.model.valueobject.Document;
import com.algashop.ordering.domain.model.valueobject.Email;
import com.algashop.ordering.domain.model.valueobject.FullName;
import com.algashop.ordering.domain.model.valueobject.LoyaltPoints;
import com.algashop.ordering.domain.model.valueobject.Phone;
import com.algashop.ordering.domain.model.valueobject.ZipCode;
import com.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerPersistenceEntityDisassembler {
  public Customer toDomainEntity(CustomerPersistenceEntity entity) {
    return Customer.existingCustomer()
            .id(new CustomerId(entity.getId()))
            .fullName(new FullName(entity.getFirstName(), entity.getLastName()))
            .birthDate(new BirthDate(entity.getBirthDate()))
            .email(new Email(entity.getEmail()))
            .phone(new Phone(entity.getPhone()))
            .document(new Document(entity.getDocument()))
            .promotionNotificationsAllowed(entity.getPromotionNotificationsAllowed())
            .registeredAt(entity.getRegisteredAt())
            .archived(entity.getArchived())
            .archivedAt(entity.getArchivedAt())
            .loyaltyPoints(new LoyaltPoints(entity.getLoyaltyPoints()))
            .address(toAddress(entity.getAddress()))
            .build();
  }

  private Address toAddress(AddressEmbeddable embeddable) {
    return Address.builder()
            .street(embeddable.getStreet())
            .number(embeddable.getNumber())
            .complement(embeddable.getComplement())
            .neighborhood(embeddable.getNeighborhood())
            .city(embeddable.getCity())
            .state(embeddable.getState())
            .zipCode(new ZipCode(embeddable.getZipCode()))
            .build();
  }

}
