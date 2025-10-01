package com.algashop.ordering.domain.model.valueobject;

import com.algashop.ordering.domain.model.utility.validator.FieldValidations;
import lombok.Builder;

import java.util.Objects;

public record Address(String street,
                      String number,
                      String complement,
                      String neighborhood,
                      String city,
                      String state,
                      Zipcode zipcode) {

  @Builder(toBuilder = true)
  public Address {
    FieldValidations.requiresNonBlank(street);
    FieldValidations.requiresNonBlank(number);
    FieldValidations.requiresNonBlank(neighborhood);
    FieldValidations.requiresNonBlank(city);
    FieldValidations.requiresNonBlank(state);
    Objects.requireNonNull(zipcode);
  }

  @Override
  public String toString() {
    return  "street='" + street + '\'' +
            ", number=" + number +
            ", complement='" + complement + '\'' +
            ", neighborhood='" + neighborhood + '\'' +
            ", city='" + city + '\'' +
            ", state='" + state + '\'' +
            ", zipcode=" + zipcode;
  }
}
