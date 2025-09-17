package com.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyTest {

  @Test
  void shouldGenerateWithValue() {
    Money money = new Money(BigDecimal.TEN);
    Assertions.assertThat(money.value()).isEqualTo(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_EVEN));
  }

  @Test
  void shouldGenerateWithValueWithConstructText() {
    Money money = new Money("10");
    Assertions.assertThat(money.value()).isEqualTo(BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_EVEN));
  }

  @Test
  void shouldGenerateException() {
    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Money(BigDecimal.valueOf(-10)));
  }

  @Test
  void shouldAddValue() {
    Money money = new Money(BigDecimal.TEN);
    Money added = money.add(new Money(BigDecimal.TEN));
    Assertions.assertThat(added.value()).isEqualTo(BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_EVEN));
  }

  @Test
  void shouldMultiplyValue() {
    Money money = new Money(BigDecimal.TEN);
    Money result = money.multiply(new Quantity(2));
    Assertions.assertThat(result.value()).isEqualTo(BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_EVEN));
  }

  @Test
  void shouldMultiplyValueThrowException() {
    Money money = new Money(BigDecimal.TEN);

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> money.multiply(new Quantity(0)));
  }

  @Test
  void shouldDivideValue() {
    Money money = new Money(BigDecimal.TEN);
    Money result = money.divide(new Money(BigDecimal.valueOf(2)));
    Assertions.assertThat(result.value()).isEqualTo(BigDecimal.valueOf(5).setScale(2, RoundingMode.HALF_EVEN));
  }

}
