package com.algashop.ordering.domain.model.valueobject;

import com.algashop.ordering.domain.model.exception.ErrorMessages;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(BigDecimal value) implements Comparable<Money> {

  private static final RoundingMode HALF_EVEN = RoundingMode.HALF_EVEN;
  public static final Money ZERO = new Money(BigDecimal.ZERO);

  public Money(BigDecimal value) {
    Objects.requireNonNull(value, ErrorMessages.VALIDATION_ERROR_MONEY_IS_NULL);
    if (value.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException();
    }
    this.value = value.setScale(2, HALF_EVEN);
  }

  public Money(String value) {
    this(new BigDecimal(value).setScale(2, HALF_EVEN));
  }

  public Money multiply(Quantity quantity) {
    Objects.requireNonNull(quantity);
    if (quantity.value() < 1) {
      throw new IllegalArgumentException(ErrorMessages.VALIDATION_ERROR_QUANTITY_MUST_POSITIVE);
    }
    return new Money(this.value().multiply(BigDecimal.valueOf(quantity.value())));
  }

  public Money add(Money other) {
    Objects.requireNonNull(other);
    return new Money(this.value().add(other.value()));
  }

  public Money divide(Money other) {
    Objects.requireNonNull(other);
    return new Money(this.value.divide(other.value(), HALF_EVEN));
  }

  @Override
  public int compareTo(Money other) {
    return this.value().compareTo(other.value());
  }

  @Override
  public String toString() {
    return value().setScale(2, HALF_EVEN).toString();
  }
}
