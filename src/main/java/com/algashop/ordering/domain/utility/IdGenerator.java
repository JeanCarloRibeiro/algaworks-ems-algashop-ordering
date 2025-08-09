package com.algashop.ordering.domain.utility;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;

import java.util.UUID;

public class IdGenerator {
  private static final TimeBasedEpochRandomGenerator RANDOM_GENERATOR = Generators.timeBasedEpochRandomGenerator();

  public IdGenerator() {
  }

  public static UUID generateTimeBasedUUID() {
    return RANDOM_GENERATOR.generate();
  }

}
