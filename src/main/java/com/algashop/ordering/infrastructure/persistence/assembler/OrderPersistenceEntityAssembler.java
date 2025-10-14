package com.algashop.ordering.infrastructure.persistence.assembler;

import com.algashop.ordering.domain.model.entity.Order;
import com.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.algashop.ordering.infrastructure.persistence.embeddable.BillingEmbeddable;
import com.algashop.ordering.infrastructure.persistence.embeddable.RecipientEmbeddable;
import com.algashop.ordering.infrastructure.persistence.embeddable.ShippingEmbeddable;
import com.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderPersistenceEntityAssembler {

  public OrderPersistenceEntity fromDomain(Order order) {
    return merge(new OrderPersistenceEntity(), order);
  }

  public OrderPersistenceEntity merge(OrderPersistenceEntity entity, Order order) {
    entity.setId(order.id().value().toLong());
    entity.setCustomerId(order.customerId().value());
    entity.setTotalAmount(order.totalAmount().value());
    entity.setTotalItems(order.totalItems().value());
    entity.setStatus(order.status().name());
    entity.setPaymentMethod(order.paymentMethod().name());
    entity.setPlacedAt(order.placedAt());
    entity.setPaidAt(order.paidAt());
    entity.setCanceledAt(order.canceledAt());
    entity.setReadAt(order.readyAt());
    entity.setVersion(order.version());

    entity.setBilling(BillingEmbeddable.builder()
            .firstName(order.billing().fullName().firstName())
            .lastName(order.billing().fullName().lastName())
            .document(order.billing().document().value())
            .phone(order.billing().phone().value())
            .email(order.billing().email().value())
            .address(AddressEmbeddable.builder()
                    .street(order.billing().address().street())
                    .number(order.billing().address().number())
                    .complement(order.billing().address().complement())
                    .neighborhood(order.billing().address().neighborhood())
                    .city(order.billing().address().city())
                    .state(order.billing().address().state())
                    .zipCode(order.billing().address().zipCode().value())
                    .build()).build());

    entity.setShipping(ShippingEmbeddable.builder()
                    .cost(order.shipping().cost().value())
                    .expectedDate(order.shipping().expectedDate())
                    .recipient(RecipientEmbeddable.builder()
                            .firstName(order.shipping().recipient().fullName().firstName())
                            .lastName(order.shipping().recipient().fullName().lastName())
                            .document(order.shipping().recipient().document().value())
                            .phone(order.shipping().recipient().phone().value())
                            .build())
                    .address(AddressEmbeddable.builder()
                            .street(order.shipping().address().street())
                            .number(order.shipping().address().number())
                            .complement(order.shipping().address().complement())
                            .neighborhood(order.shipping().address().neighborhood())
                            .city(order.shipping().address().city())
                            .state(order.shipping().address().state())
                            .zipCode(order.shipping().address().zipCode().value())
                            .build())
            .build());

    return entity;
  }

}
