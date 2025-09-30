package com.algashop.ordering.domain.valueobject;

import com.algashop.ordering.domain.entity.databuilder.OrderTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BillingTest {
  @Test
  void shouldGenerateWithValue() {
    Billing billing = OrderTestDataBuilder.billing();

    Assertions.assertThat(billing).isNotNull();
    Assertions.assertThat(billing.fullName().toString()).hasToString("Jean Carlo Ribeiro");
  }

}
