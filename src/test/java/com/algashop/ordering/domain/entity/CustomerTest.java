package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.utility.IdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomerTest {
  @Test
  void testingCustomer() {
    Customer customer = new Customer(
            IdGenerator.generateTimeBasedUUID(),
            "Jean Carlo",
            LocalDate.of(1986, 2, 10),
            "jean.ribeiro@test.com",
            "478-256-2504",
            "25508-0578",
            true,
            OffsetDateTime.now()
    );
    customer.addLoyaltyPoints(10);

    Assertions.assertNotNull(customer);
    Assertions.assertEquals("jean.ribeiro@test.com", customer.email());
  }

}
