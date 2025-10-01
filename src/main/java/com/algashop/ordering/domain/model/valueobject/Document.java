package com.algashop.ordering.domain.model.valueobject;

import com.algashop.ordering.domain.model.exception.ErrorMessages;

import java.util.Objects;

public record Document(String value) {
  public Document(String value) {
    Objects.requireNonNull(value, ErrorMessages.VALIDATION_ERROR_DOCUMENT_IS_NULL);
    if (value.isBlank()) {
      throw new IllegalArgumentException(ErrorMessages.VALIDATION_ERROR_DOCUMENT_IS_BLANK);
    }
    this.value = value.trim();
  }

  @Override
  public String toString() {
    return value();
  }

}
