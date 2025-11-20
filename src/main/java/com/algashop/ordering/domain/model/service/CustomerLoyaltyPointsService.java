package com.algashop.ordering.domain.model.service;

import com.algashop.ordering.domain.model.entity.Customer;
import com.algashop.ordering.domain.model.entity.Order;
import com.algashop.ordering.domain.model.exception.CantAddLoyaltPointOrderIsNotReadyException;
import com.algashop.ordering.domain.model.exception.OrderNotBelongsToCustomerException;
import com.algashop.ordering.domain.model.valueobject.LoyaltPoints;
import com.algashop.ordering.domain.model.valueobject.Money;

import java.util.Objects;

public class CustomerLoyaltyPointsService {

  private static final LoyaltPoints BASE_POINTS = new LoyaltPoints(5);
  private static final Money EXPECTED_AMOUNT_TO_GIVE_POINTS = new Money("1000");
  public void addPoints(Customer customer, Order order) {
    Objects.requireNonNull(customer);
    Objects.requireNonNull(order);

    if (!customer.id().equals(order.customerId())) {
      throw new OrderNotBelongsToCustomerException();
    }
    if (!order.isReady()) {
      throw new CantAddLoyaltPointOrderIsNotReadyException();
    }

    customer.addLoyaltyPoints(calculatePoints(order));
  }

  private LoyaltPoints calculatePoints(Order order) {
    if (shouldGivePointsByAmount(order.totalAmount())) {
      Money result = order.totalAmount().divide(EXPECTED_AMOUNT_TO_GIVE_POINTS);
      return new LoyaltPoints(result.value().intValue() * BASE_POINTS.value());
    }
    return LoyaltPoints.ZERO;
  }

  private boolean shouldGivePointsByAmount(Money amount) {
    return amount.compareTo(EXPECTED_AMOUNT_TO_GIVE_POINTS) >= 0;
  }

}
