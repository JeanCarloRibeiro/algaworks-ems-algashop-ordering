package com.algashop.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmailTest {

  @Test
  void shouldGenerateWithValue() {
    Email email = new Email("jean@test.com");
    Assertions.assertThat(email.value()).isEqualTo("jean@test.com");
    Assertions.assertThat(email.toString()).hasToString("jean@test.com");
  }

  @Test
  void shouldGenerateException() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Email(""));

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Email("jean@"));
  }

}
