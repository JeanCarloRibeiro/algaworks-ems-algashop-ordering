package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.utility.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomerTest {
  @Test
  void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> {
          new Customer(
                  IdGenerator.generateTimeBasedUUID(),
                  "Jean Carlo",
                  LocalDate.of(1986, 2, 10),
                  "invalid",
                  "478-256-2504",
                  "25508-0578",
                  false,
                  OffsetDateTime.now());
        });
  }

  @Test
  void given_invalidEmail_whenTryUpdateCustomer_shouldGenerateException() {

    Customer customer = new Customer(
            IdGenerator.generateTimeBasedUUID(),
            "Jean Carlo",
            LocalDate.of(1986, 2, 10),
            "jean@test.com",
            "478-256-2504",
            "25508-0578",
            false,
            OffsetDateTime.now());

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
              customer.changeEmail("invalid");
            });
  }

}
