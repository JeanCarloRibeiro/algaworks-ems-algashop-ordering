package com.algashop.ordering.domain.valueobject;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

public record Shipping(
        Money cost,
        LocalDate expectedDate) {

  @Builder(toBuilder = true)
  public Shipping {
    Objects.requireNonNull(cost);
    Objects.requireNonNull(expectedDate);
  }

}
