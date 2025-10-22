package com.algashop.ordering.domain.model.entity.databuilder;

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

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomerTestDataBuilder {
  public CustomerTestDataBuilder() {
  }

  public static Customer.BrandNewCustomerBuild brandNewCustomer() {
    return Customer.brandNew()
            .fullName(new FullName("Jean Carlo", "Ribeiro"))
            .birthDate(new BirthDate(LocalDate.of(1986, 2, 10)))
            .email(new Email("jean@test.com"))
            .phone(new Phone("478-256-2504"))
            .document(new Document("255-08-0578"))
            .promotionNotificationsAllowed(false)
            .address(Address.builder()
                    .street("Bourbon Street")
                    .number("100")
                    .complement("House 1")
                    .neighborhood("North Ville")
                    .city("New York")
                    .state("South California")
                    .zipCode(new ZipCode("12345"))
                    .build());
  }

  public static Customer.ExistingCustomerBuild existingCustomer() {
    return Customer.existingCustomer()
            .id(new CustomerId())
            .registeredAt(OffsetDateTime.now())
            .archived(false)
            .archivedAt(null)
            .fullName(new FullName("Jean Carlo", "Ribeiro"))
            .birthDate(new BirthDate(LocalDate.of(1986, 2, 10)))
            .email(new Email("jean@test.com"))
            .phone(new Phone("478-256-2504"))
            .document(new Document("255-08-0578"))
            .promotionNotificationsAllowed(false)
            .loyaltyPoints(LoyaltPoints.ZERO)
            .address(Address.builder()
                    .street("Bourbon Street")
                    .number("100")
                    .complement("House 1")
                    .neighborhood("North Ville")
                    .city("New York")
                    .state("South California")
                    .zipCode(new ZipCode("12345"))
                    .build());
  }

  public static Customer.ExistingCustomerBuild existingAnonymizedCustomer() {
    return Customer.existingCustomer()
            .id(new CustomerId())
            .fullName(new FullName("Anonymous", "Anonymous"))
            .birthDate(null)
            .email(new Email("anonymous@Anonymous.com"))
            .phone(new Phone("000-000-0000"))
            .document(new Document("000-00-0000"))
            .promotionNotificationsAllowed(false)
            .registeredAt(OffsetDateTime.now())
            .archived(true)
            .archivedAt(OffsetDateTime.now())
            .loyaltyPoints(new LoyaltPoints(10))
            .address(Address.builder()
                    .street("Bourbon Street")
                    .number("100")
                    .complement("House 1")
                    .neighborhood("North Ville")
                    .city("New York")
                    .state("South California")
                    .zipCode(new ZipCode("12345"))
                    .build());
  }


}
