package com.algashop.ordering.domain.model.service;

import com.algashop.ordering.domain.model.entity.Customer;
import com.algashop.ordering.domain.model.repository.Customers;
import com.algashop.ordering.domain.model.valueobject.Address;
import com.algashop.ordering.domain.model.valueobject.BirthDate;
import com.algashop.ordering.domain.model.valueobject.Document;
import com.algashop.ordering.domain.model.valueobject.Email;
import com.algashop.ordering.domain.model.valueobject.FullName;
import com.algashop.ordering.domain.model.valueobject.Phone;
import com.algashop.ordering.domain.model.valueobject.ZipCode;
import com.algashop.ordering.domain.model.valueobject.id.CustomerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerRegistrationServiceTest {

  @Mock
  private Customers customers;

  @InjectMocks
  private CustomerRegistrationService customerRegistrationService;

  @Test
  void shouldRegister() {
    when(customers.isEmailUnique(any(Email.class), any(CustomerId.class))).thenReturn(true);

    Customer customer = customerRegistrationService.register(
            new FullName("John", "Doe"), new BirthDate(LocalDate.of(1991, 7, 5)),
            new Email("johndoe@email.com"), new Phone("478-256-2604"),
            new Document("255-08-0578"),
            true,
            Address.builder()
                    .street("Bourbon Street")
                    .number("1234")
                    .neighborhood("North Ville")
                    .city("Montfort")
                    .state("South Carolina")
                    .zipCode(new ZipCode("79911"))
                    .complement("House. 1")
                    .build());

    Assertions.assertThat(customer.fullName()).isEqualTo(new FullName("John", "Doe"));
  }

}