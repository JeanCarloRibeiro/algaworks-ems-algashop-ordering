package com.algashop.ordering.domain.model.service;

import com.algashop.ordering.domain.model.entity.Customer;
import com.algashop.ordering.domain.model.entity.Order;
import com.algashop.ordering.domain.model.entity.databuilder.CustomerTestDataBuilder;
import com.algashop.ordering.domain.model.entity.databuilder.OrderTestDataBuilder;
import com.algashop.ordering.domain.model.entity.databuilder.ProductTestDataBuilder;
import com.algashop.ordering.domain.model.enums.OrderStatus;
import com.algashop.ordering.domain.model.valueobject.LoyaltPoints;
import com.algashop.ordering.domain.model.valueobject.Quantity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerLoyaltyPointsServiceTest {

  private final CustomerLoyaltyPointsService customerLoyaltyPointsService = new CustomerLoyaltyPointsService();

  @Test
  public void givenValidCustomerAndOrderWhenAddingPointsShouldAccumulate() {
    Customer customer = CustomerTestDataBuilder.existingCustomer().build();
    Order order = OrderTestDataBuilder.Order().orderStatus(OrderStatus.READY).build();

    customerLoyaltyPointsService.addPoints(customer, order);

    Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltPoints(30));
  }

  @Test
  public void givenValidCustomerAndOrderWithLowTotalAmountWhenAddingPointsShouldNotAccumulate() {
    Customer customer = CustomerTestDataBuilder.existingCustomer().build();
    Order order = OrderTestDataBuilder.Order().orderStatus(OrderStatus.DRAFT).withItems(false).build();
    order.addItem(ProductTestDataBuilder.productAltMousePad().build(), new Quantity(1));
    order.place();
    order.markAsPaid();
    order.markAsReady();

    customerLoyaltyPointsService.addPoints(customer, order);

    Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltPoints(0));

  }

}