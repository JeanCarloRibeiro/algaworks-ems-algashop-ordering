package com.algashop.ordering.domain.model.service;

import com.algashop.ordering.domain.model.entity.Customer;
import com.algashop.ordering.domain.model.exception.CustomerEmailInUseException;
import com.algashop.ordering.domain.model.repository.Customers;
import com.algashop.ordering.domain.model.valueobject.Address;
import com.algashop.ordering.domain.model.valueobject.BirthDate;
import com.algashop.ordering.domain.model.valueobject.Document;
import com.algashop.ordering.domain.model.valueobject.Email;
import com.algashop.ordering.domain.model.valueobject.FullName;
import com.algashop.ordering.domain.model.valueobject.Phone;
import com.algashop.ordering.domain.model.valueobject.id.CustomerId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerRegistrationService {

  private final Customers customers;

  public Customer register(
          FullName fullName,
          BirthDate birthDate,
          Email email,
          Phone phone,
          Document document,
          Boolean promotionNotificationsAllowed,
          Address address
  ) {
    Customer customer = Customer.brandNew()
            .fullName(fullName)
            .birthDate(birthDate)
            .email(email)
            .phone(phone)
            .document(document)
            .promotionNotificationsAllowed(promotionNotificationsAllowed)
            .address(address)
            .build();

    verifyEmailUniqueness(customer.email(), customer.id());

    return customer;
  }

  public void changeEmail(Customer customer, Email email) {
    verifyEmailUniqueness(email, customer.id());
    customer.changeEmail(email);
  }

  private void verifyEmailUniqueness(Email email, CustomerId id) {
    if (!customers.isEmailUnique(email, id)) {
      throw new CustomerEmailInUseException();
    }
  }

}
