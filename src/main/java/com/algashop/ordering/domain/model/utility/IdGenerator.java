package com.algashop.ordering.domain.model.utility;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;
import io.hypersistence.tsid.TSID;

import java.util.UUID;

public class IdGenerator {
  private static final TimeBasedEpochRandomGenerator RANDOM_GENERATOR = Generators.timeBasedEpochRandomGenerator();
  private static final TSID.Factory TSID_FACTORY = TSID.Factory.INSTANCE;

  public IdGenerator() {
  }

  public static UUID generateTimeBasedUUID() {
    return RANDOM_GENERATOR.generate();
  }

  /*
  * TSID_NODE=0 (INSTANCE 1)
  * TSID_NODE_COUNT=3 (INSTANCES)
  */
  public static TSID generateTSID() {
    return TSID_FACTORY.generate();
  }

}
