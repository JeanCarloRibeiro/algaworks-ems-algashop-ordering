package com.algashop.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DocumentTest {

  @Test
  void shouldGenerateWithValue() {
    Document document = new Document("255-08-0578");
    Assertions.assertThat(document.value()).isEqualTo("255-08-0578");
    Assertions.assertThat(document.toString()).hasToString("255-08-0578");
  }

  @Test
  void shouldGenerateException() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Document(""));

    Assertions.assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> new Document(null));
  }

}
