package com.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BillingInfoTest {
  @Test
  void shouldGenerateWithValue() {
    Billing billingInfo = Billing.builder()
            .fullName(new FullName("Jean Carlo", "Ribeiro"))
            .document(new Document("255-08-0578"))
            .phone(new Phone("478-256-2504"))
            .address(Address.builder()
                            .street("Bourbon Street")
                            .number("100")
                            .complement("House 1")
                            .neighborhood("North Ville")
                            .city("New York")
                            .state("South California")
                            .zipcode(new Zipcode("12345"))
                            .build())
            .build();
    Assertions.assertThat(billingInfo).isNotNull();
    Assertions.assertThat(billingInfo.fullName().toString()).isEqualTo("Jean Carlo Ribeiro");
  }

}
