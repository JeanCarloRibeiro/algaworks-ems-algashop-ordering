package com.algashop.ordering.domain.model.valueobject;

import java.time.LocalDate;
import java.util.Objects;

import static com.algashop.ordering.domain.model.exception.ErrorMessages.VALIDATION_ERROR_BIRTHDATE_IS_NULL;
import static com.algashop.ordering.domain.model.exception.ErrorMessages.VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST;

public record BirthDate(LocalDate value) {
  public BirthDate(LocalDate value) {
    Objects.requireNonNull(value, VALIDATION_ERROR_BIRTHDATE_IS_NULL);
    if (value.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException(VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST);
    }
    this.value = value;
  }

  public Integer age() {
    return LocalDate.now().minusYears(this.value().getYear()).getYear();
  }

  @Override
  public String toString() {
    return value().toString();
  }

}
