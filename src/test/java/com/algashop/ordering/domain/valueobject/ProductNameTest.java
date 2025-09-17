package com.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductNameTest {
  @Test
  void shouldGenerateWithValue() {
    ProductName name = new ProductName("Product");
    Assertions.assertThat(name.value()).isEqualTo("Product");
  }

  @Test
  void shouldGenerateException() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new ProductName(""));
  }

}
