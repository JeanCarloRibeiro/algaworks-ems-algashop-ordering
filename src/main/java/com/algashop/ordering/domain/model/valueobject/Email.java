package com.algashop.ordering.domain.model.valueobject;

import com.algashop.ordering.domain.model.exception.ErrorMessages;
import com.algashop.ordering.domain.model.utility.validator.FieldValidations;

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
