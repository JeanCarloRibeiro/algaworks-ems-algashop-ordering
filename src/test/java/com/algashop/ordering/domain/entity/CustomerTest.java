package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algashop.ordering.domain.utility.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

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
                  "255-08-0578",
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
            "255-08-0578",
            false,
            OffsetDateTime.now());

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
              customer.changeEmail("invalid");
            });
  }

  @Test
  void given_unarchivedCustomer_whenArchive_shouldAnonymize() {
    Customer customer = new Customer(
            IdGenerator.generateTimeBasedUUID(),
            "Jean Carlo",
            LocalDate.of(1986, 2, 10),
            "jean@test.com",
            "478-256-2504",
            "255-08-0578",
            false,
            OffsetDateTime.now());

    customer.archive();

    Assertions.assertWith(customer,
            c -> assertThat(c.fullName()).isEqualTo("Anonymous"),
            c -> assertThat(c.email()).isNotEqualTo("jean@test.com"),
            c -> assertThat(c.phone()).isEqualTo("000-000-0000"),
            c -> assertThat(c.document()).isEqualTo("000-00-0000"),
            c -> assertThat(c.birthDate()).isNull(),
            c -> assertThat(c.isPromotionNotificationsAllowed()).isFalse()
    );
  }

  @Test
  void given_archivedCustomer_whenTryToUpdate_shouldGenerateException() {
    Customer customer = new Customer(
            IdGenerator.generateTimeBasedUUID(),
            "Jean Carlo",
            LocalDate.of(1986, 2, 10),
            "jean@test.com",
            "478-256-2504",
            "255-08-0578",
            false,
            OffsetDateTime.now(),
            true,
            OffsetDateTime.now(),
            10);

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(customer::archive);

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(() -> customer.changeName("Test"));

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(() -> customer.changePhone("123-123-1111"));

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(() -> customer.changeEmail("test@test.com"));

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(customer::enablePromotionNotifications);

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(customer::disablePromotionNotifications);

  }

  @Test
  void given_brandCustomer_whenAddLoyaltPoints_shouldSumPoints() {
    Customer customer = new Customer(
            IdGenerator.generateTimeBasedUUID(),
            "Jean Carlo",
            LocalDate.of(1986, 2, 10),
            "jean@test.com",
            "478-256-2504",
            "255-08-0578",
            false,
            OffsetDateTime.now());

    customer.addLoyaltyPoints(10);
    customer.addLoyaltyPoints(20);

    Assertions.assertWith(customer,
            c -> assertThat(c.loyaltyPoints()).isEqualTo(30)
    );
  }

  @Test
  void given_brandCustomer_whenAddInvalidLoyaltPoints_shouldGenerateException() {
    Customer customer = new Customer(
            IdGenerator.generateTimeBasedUUID(),
            "Jean Carlo",
            LocalDate.of(1986, 2, 10),
            "jean@test.com",
            "478-256-2504",
            "255-08-0578",
            false,
            OffsetDateTime.now());

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> customer.addLoyaltyPoints(0));

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> customer.addLoyaltyPoints(-10));
  }

}
