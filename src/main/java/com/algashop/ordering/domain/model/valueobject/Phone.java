package com.algashop.ordering.domain.model.valueobject;

import java.util.Objects;

import static com.algashop.ordering.domain.model.exception.ErrorMessages.VALIDATION_ERROR_PHONE_IS_BLANK;
import static com.algashop.ordering.domain.model.exception.ErrorMessages.VALIDATION_ERROR_PHONE_IS_NULL;

public record Phone(String value) {
  public Phone(String value) {
    Objects.requireNonNull(value, VALIDATION_ERROR_PHONE_IS_NULL);
    if (value.isBlank()) {
      throw new IllegalArgumentException(VALIDATION_ERROR_PHONE_IS_BLANK);
    }
    this.value = value;
  }

  @Override
  public String toString() {
    return value();
  }

}
