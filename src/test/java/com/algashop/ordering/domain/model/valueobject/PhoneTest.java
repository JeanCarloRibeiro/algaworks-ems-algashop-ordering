package com.algashop.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PhoneTest {

  @Test
  void shouldGenerateWithValue() {
    Phone phone = new Phone("478-256-2504");
    Assertions.assertThat(phone).isEqualTo(new Phone("478-256-2504"));
    Assertions.assertThat(phone.toString()).hasToString("478-256-2504");
  }

  @Test
  void shouldGenerateException() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Phone(""));

    Assertions.assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> new Phone(null));
  }

}
