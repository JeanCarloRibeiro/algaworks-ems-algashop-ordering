package com.algashop.ordering.domain.valueobject;

import lombok.Builder;

import java.util.Objects;

public record Billing(FullName fullName,
                      Document document,
                      Phone phone,
                      Address address) {

  @Builder(toBuilder = true)
  public Billing {
    Objects.requireNonNull(fullName);
    Objects.requireNonNull(document);
    Objects.requireNonNull(phone);
    Objects.requireNonNull(address);
  }

}
