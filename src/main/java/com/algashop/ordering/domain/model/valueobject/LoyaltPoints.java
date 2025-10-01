package com.algashop.ordering.domain.model.valueobject;

import java.util.Objects;

public record LoyaltPoints(Integer value) implements Comparable<LoyaltPoints> {

  public static final LoyaltPoints ZERO = new LoyaltPoints(0);

  public LoyaltPoints() {
    this(0);
  }
  public LoyaltPoints(Integer value) {
    Objects.requireNonNull(value);
    if (value < 0) {
      throw new IllegalArgumentException();
    }
    this.value = value;
  }

  public LoyaltPoints add(Integer value) {
    return add(new LoyaltPoints(value));
  }

  private LoyaltPoints add(LoyaltPoints loyaltPoints) {
    Objects.requireNonNull(loyaltPoints);
    if (loyaltPoints.value() <= 0) {
      throw new IllegalArgumentException();
    }
    return new LoyaltPoints(this.value() + loyaltPoints.value());
  }

  @Override
  public String toString() {
    return value.toString();
  }

  @Override
  public int compareTo(LoyaltPoints o) {
    return this.value().compareTo(o.value());
  }
}
