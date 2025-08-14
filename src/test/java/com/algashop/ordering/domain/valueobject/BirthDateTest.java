package com.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class BirthDateTest {

  @Test
  void shouldGenerateWithValue() {
    BirthDate birthDate = new BirthDate(LocalDate.of(1986, 2, 10));
    Assertions.assertThat(birthDate.value()).isEqualTo(LocalDate.of(1986, 2, 10));
    Assertions.assertThat(birthDate.toString()).hasToString("1986-02-10");

  }

  @Test
  void given_birthDate_shouldReturnAgeWithValue() {
    BirthDate birthDate = new BirthDate(LocalDate.of(1986, 2, 10));
    Assertions.assertThat(birthDate.age()).isEqualTo(39);
  }

  @Test
  void shouldGenerateException() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BirthDate(LocalDate.of(2026, 2, 10)));

    Assertions.assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> new BirthDate(null));
  }

}
