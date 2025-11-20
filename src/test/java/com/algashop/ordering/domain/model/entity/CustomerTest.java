package com.algashop.ordering.domain.model.entity;

import com.algashop.ordering.domain.model.entity.databuilder.CustomerTestDataBuilder;
import com.algashop.ordering.domain.model.exception.CustomerArchivedException;
import com.algashop.ordering.domain.model.valueobject.Address;
import com.algashop.ordering.domain.model.valueobject.Email;
import com.algashop.ordering.domain.model.valueobject.FullName;
import com.algashop.ordering.domain.model.valueobject.LoyaltPoints;
import com.algashop.ordering.domain.model.valueobject.Phone;
import com.algashop.ordering.domain.model.valueobject.ZipCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTest {
  @Test
  void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> {
          CustomerTestDataBuilder.brandNewCustomer()
                  .email(new Email("invalid"))
                  .build();
        });
  }

  @Test
  void given_invalidEmail_whenTryUpdateCustomer_shouldGenerateException() {
    Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
              customer.changeEmail(new Email("invalid"));
            });
  }

  @Test
  void given_unarchivedCustomer_whenArchive_shouldAnonymize() {
    Customer customer = CustomerTestDataBuilder.existingCustomer().build();
    customer.archive();

    Assertions.assertWith(customer,
            c -> assertThat(c.fullName().firstName()).isEqualTo("Anonymous"),
            c -> assertThat(c.email().value()).isNotEqualTo(new Email("jean@test.com").toString()),
            c -> assertThat(c.phone().value()).isEqualTo(new Phone("000-000-0000").toString()),
            c -> assertThat(c.document().value()).isEqualTo("000-00-0000"),
            c -> assertThat(c.birthDate()).isNull(),
            c -> assertThat(c.isPromotionNotificationsAllowed()).isFalse(),
            c -> Assertions.assertThat(c.address()).isEqualTo(
                    Address.builder()
                            .street("Bourbon Street")
                            .number("Anonymized")
                            .complement(null)
                            .neighborhood("North Ville")
                            .city("New York")
                            .state("South California")
                            .zipCode(new ZipCode("12345"))
                            .build()
            )
    );
  }

  @Test
  void given_archivedCustomer_whenTryToUpdate_shouldGenerateException() {
    Customer customer = CustomerTestDataBuilder.existingAnonymizedCustomer().build();

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
    Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

    customer.addLoyaltyPoints(new LoyaltPoints(10));
    customer.addLoyaltyPoints(new LoyaltPoints(20));

    Assertions.assertWith(customer,
            c -> assertThat(c.loyaltyPoints().value()).isEqualTo(30)
    );
  }

  @Test
  void given_brandCustomer_whenAddInvalidLoyaltPoints_shouldGenerateException() {
    Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> customer.addLoyaltyPoints(new LoyaltPoints(-10)));
  }

}
