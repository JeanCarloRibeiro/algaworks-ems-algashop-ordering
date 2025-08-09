package com.algashop.ordering.domain.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class CustomerTest {
  @Test
  void testingCustomer() {
    Customer customer = new Customer();
    customer.setId(UUID.randomUUID());
    customer.setFullName("Jean Carlo");
    customer.setDocument("1234");
    customer.setLoyaltyPoints(10);
  }
}
