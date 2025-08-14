package com.algashop.ordering.domain.entity;

import com.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algashop.ordering.domain.valueobject.BirthDate;
import com.algashop.ordering.domain.valueobject.CustomerId;
import com.algashop.ordering.domain.valueobject.Document;
import com.algashop.ordering.domain.valueobject.Email;
import com.algashop.ordering.domain.valueobject.FullName;
import com.algashop.ordering.domain.valueobject.LoyaltPoints;
import com.algashop.ordering.domain.valueobject.Phone;
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
                  new CustomerId(),
                  new FullName("Jean Carlo", "Ribeiro"),
                  new BirthDate(LocalDate.of(1986, 2, 10)),
                  new Email("invalid"),
                  new Phone("478-256-2504"),
                  new Document("255-08-0578"),
                  false,
                  OffsetDateTime.now());
        });
  }

  @Test
  void given_invalidEmail_whenTryUpdateCustomer_shouldGenerateException() {

    Customer customer = new Customer(
            new CustomerId(),
            new FullName("Jean Carlo", "Ribeiro"),
            new BirthDate(LocalDate.of(1986, 2, 10)),
            new Email("jean@test.com"),
            new Phone("478-256-2504"),
            new Document("255-08-0578"),
            false,
            OffsetDateTime.now());

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
              customer.changeEmail(new Email("invalid"));
            });
  }

  @Test
  void given_unarchivedCustomer_whenArchive_shouldAnonymize() {
    Customer customer = new Customer(
            new CustomerId(),
            new FullName("Jean Carlo", "Ribeiro"),
            new BirthDate(LocalDate.of(1986, 2, 10)),
            new Email("jean@test.com"),
            new Phone("478-256-2504"),
            new Document("255-08-0578"),
            false,
            OffsetDateTime.now());

    customer.archive();

    Assertions.assertWith(customer,
            c -> assertThat(c.fullName().firstName()).isEqualTo("Anonymous"),
            c -> assertThat(c.email().value()).isNotEqualTo(new Email("jean@test.com").toString()),
            c -> assertThat(c.phone().value()).isEqualTo(new Phone("000-000-0000").toString()),
            c -> assertThat(c.document().value()).isEqualTo("000-00-0000"),
            c -> assertThat(c.birthDate()).isNull(),
            c -> assertThat(c.isPromotionNotificationsAllowed()).isFalse()
    );
  }

  @Test
  void given_archivedCustomer_whenTryToUpdate_shouldGenerateException() {
    Customer customer = new Customer(
            new CustomerId(),
            new FullName("Jean Carlo", "Ribeiro"),
            new BirthDate(LocalDate.of(1986, 2, 10)),
            new Email("jean@test.com"),
            new Phone("478-256-2504"),
            new Document("255-08-0578"),
            false,
            OffsetDateTime.now(),
            true,
            OffsetDateTime.now(),
            new LoyaltPoints(10));

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(customer::archive);

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(() -> customer.changeName(new FullName("Jean Carlo", "Ribeiro")));

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(() -> customer.changePhone(new Phone("123-123-1111")));

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(() -> customer.changeEmail(new Email("test@test.com")));

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(customer::enablePromotionNotifications);

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(customer::disablePromotionNotifications);

  }

  @Test
  void given_brandCustomer_whenAddLoyaltPoints_shouldSumPoints() {
    Customer customer = new Customer(
            new CustomerId(),
            new FullName("Jean Carlo", "Ribeiro"),
            new BirthDate(LocalDate.of(1986, 2, 10)),
            new Email("jean@test.com"),
            new Phone("478-256-2504"),
            new Document("255-08-0578"),
            false,
            OffsetDateTime.now());

    customer.addLoyaltyPoints(new LoyaltPoints(10));
    customer.addLoyaltyPoints(new LoyaltPoints(20));

    Assertions.assertWith(customer,
            c -> assertThat(c.loyaltyPoints().value()).isEqualTo(30)
    );
  }

  @Test
  void given_brandCustomer_whenAddInvalidLoyaltPoints_shouldGenerateException() {
    Customer customer = new Customer(
            new CustomerId(),
            new FullName("Jean Carlo", "Ribeiro"),
            new BirthDate(LocalDate.of(1986, 2, 10)),
            new Email("jean@test.com"),
            new Phone("478-256-2504"),
            new Document("255-08-0578"),
            false,
            OffsetDateTime.now());

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> customer.addLoyaltyPoints(LoyaltPoints.ZERO));

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> customer.addLoyaltyPoints(new LoyaltPoints(-10)));
  }

}
