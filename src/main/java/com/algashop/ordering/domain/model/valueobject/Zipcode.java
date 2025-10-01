package com.algashop.ordering.domain.model.valueobject;

import java.util.Objects;

public record Zipcode(String value) {
  public Zipcode {
    Objects.requireNonNull(value);
    if (value.isBlank()) {
      throw new IllegalArgumentException();
    }
    if (value.length() != 5) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
