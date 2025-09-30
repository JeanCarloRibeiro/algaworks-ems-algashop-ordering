package com.algashop.ordering.domain.valueobject.id;

import com.algashop.ordering.domain.utility.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record ShoppingCartItemId(UUID value) {
  public ShoppingCartItemId {
    Objects.requireNonNull(value);
  }

  public ShoppingCartItemId() {
    this(IdGenerator.generateTimeBasedUUID());
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
