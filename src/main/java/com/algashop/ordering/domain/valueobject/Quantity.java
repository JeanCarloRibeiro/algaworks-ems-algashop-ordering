package com.algashop.ordering.domain.valueobject;

import java.util.Objects;

import static com.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_QUANTITY_MUST_ZERO_POSITIVE;

public record Quantity(Integer value) implements Comparable<Quantity> {

  public static final Quantity ZERO = new Quantity(0);

  public Quantity(Integer value) {
    Objects.requireNonNull(value);
    if (value < 0) {
      throw new IllegalArgumentException(VALIDATION_ERROR_QUANTITY_MUST_ZERO_POSITIVE);
    }
    this.value = value;
  }

  public Quantity add(Quantity other) {
    return new Quantity(this.value() + other.value());
  }

  @Override
  public int compareTo(Quantity other) {
    return this.value().compareTo(other.value());
  }

  @Override
  public String toString() {
    return this.value().toString();
  }

}
