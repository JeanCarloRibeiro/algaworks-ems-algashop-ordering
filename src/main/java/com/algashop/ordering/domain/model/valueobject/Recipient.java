package com.algashop.ordering.domain.model.valueobject;

import lombok.Builder;

import java.util.Objects;

public record Recipient(
        FullName fullName,
        Document document,
        Phone phone) {

  @Builder(toBuilder = true)
  public Recipient {
    Objects.requireNonNull(fullName);
    Objects.requireNonNull(document);
    Objects.requireNonNull(phone);
  }

}
