package com.algashop.ordering.domain.valueobject;

import com.algashop.ordering.domain.exception.ErrorMessages;
import com.algashop.ordering.domain.utility.validator.FieldValidations;

public record Email(String value) {
  public Email(String value) {
    FieldValidations.requiresValidEmail(value, ErrorMessages.VALIDATION_ERROR_EMAIL_IS_INVALID);
    this.value = value;
  }

  @Override
  public String toString() {
    return value();
  }

}
