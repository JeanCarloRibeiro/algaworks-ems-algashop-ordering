package com.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class QuantityTest {
  @Test
  void shouldGenerateWithValue() {
    Quantity quantity = Quantity.ZERO;
    Assertions.assertThat(quantity.value()).isEqualTo(0);
  }

  @Test
  void shouldGenerateWithValueWithConstructText() {
    Quantity money = new Quantity(10);
    Assertions.assertThat(money.value()).isEqualTo(10);
  }

  @Test
  void shouldGenerateException() {
    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Quantity(-10));
  }

  @Test
  void shouldAddValue() {
    Quantity quantity = new Quantity(10);
    Quantity added = quantity.add(new Quantity(10));
    Assertions.assertThat(added.value()).isEqualTo(20);
  }

}
