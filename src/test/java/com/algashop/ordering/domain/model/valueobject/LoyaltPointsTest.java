package com.algashop.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoyaltPointsTest {

  @Test
  void shouldGenerateWithValue() {
    LoyaltPoints loyaltPoints = new LoyaltPoints(10);
    Assertions.assertThat(loyaltPoints.value()).isEqualTo(10);
  }

  @Test
  void shouldAddValue() {
    LoyaltPoints loyaltPoints = new LoyaltPoints(10);
    Assertions.assertThat(loyaltPoints.add(10).value()).isEqualTo(20);
  }

  @Test
  void shouldNotAddValue() {
    LoyaltPoints loyaltPoints = new LoyaltPoints(10);
    Assertions.assertThat(loyaltPoints.value()).isEqualTo(10);

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> loyaltPoints.add(-10));

  }

}
