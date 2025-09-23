package com.algashop.ordering.domain.valueobject;

import lombok.Builder;

import java.util.Objects;

public record ShippingInfo(
        Address address,
        FullName fullName,
        Document document,
        Phone phone) {

  @Builder(toBuilder = true)
  public ShippingInfo {
    Objects.requireNonNull(address);
    Objects.requireNonNull(fullName);
    Objects.requireNonNull(document);
    Objects.requireNonNull(phone);
  }

  @Override
  public String toString() {
    return  "address='" + address() + '\'' +
            "fullName='" + fullName() + '\'' +
            ", document=" + document() +
            ", phone='" + phone();
  }
}
