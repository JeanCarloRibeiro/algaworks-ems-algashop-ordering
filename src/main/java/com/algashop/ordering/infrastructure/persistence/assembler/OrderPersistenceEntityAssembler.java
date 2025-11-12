package com.algashop.ordering.infrastructure.persistence.assembler;

import com.algashop.ordering.domain.model.entity.Order;
import com.algashop.ordering.domain.model.entity.OrderItem;
import com.algashop.ordering.domain.model.valueobject.Billing;
import com.algashop.ordering.domain.model.valueobject.Shipping;
import com.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.algashop.ordering.infrastructure.persistence.embeddable.BillingEmbeddable;
import com.algashop.ordering.infrastructure.persistence.embeddable.RecipientEmbeddable;
import com.algashop.ordering.infrastructure.persistence.embeddable.ShippingEmbeddable;
import com.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderPersistenceEntityAssembler {

  private final CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

  public OrderPersistenceEntity fromDomain(Order order) {
    return merge(new OrderPersistenceEntity(), order);
  }

  public OrderPersistenceEntity merge(OrderPersistenceEntity entity, Order order) {
    entity.setId(order.id().value().toLong());
    entity.setCustomer(getCustomerReference(order.customerId()));
    entity.setTotalAmount(order.totalAmount().value());
    entity.setTotalItems(order.totalItems().value());
    entity.setStatus(order.status().name());
    entity.setPaymentMethod(order.paymentMethod().name());
    entity.setPlacedAt(order.placedAt());
    entity.setPaidAt(order.paidAt());
    entity.setCanceledAt(order.canceledAt());
    entity.setReadAt(order.readyAt());
    entity.setVersion(order.version());
    entity.setBilling(toBillingEmbeddable(order.billing()));
    entity.setShipping(toShippingEmbeddable(order.shipping()));
    Set<OrderItemPersistenceEntity> mergedItems = mergeItems(entity, order);
    entity.replaceItems(mergedItems);

    return entity;
  }

  private CustomerPersistenceEntity getCustomerReference(CustomerId customerId) {
    return customerPersistenceEntityRepository.getReferenceById(customerId.value());
  }

  private Set<OrderItemPersistenceEntity> mergeItems(OrderPersistenceEntity entity, Order order) {
    Set<OrderItem> newOrUpdateItems = order.items();
    if (CollectionUtils.isEmpty(newOrUpdateItems)) {
      return new HashSet<>();
    }

    Set<OrderItemPersistenceEntity> existingItems = entity.getItems();

    if (CollectionUtils.isEmpty(existingItems)) {
      return newOrUpdateItems.stream()
              .map(this::fromDomain)
              .collect(Collectors.toSet());
    }

    Map<Long, OrderItemPersistenceEntity> existingItemMap = existingItems.stream()
            .collect(Collectors.toMap(OrderItemPersistenceEntity::getId, item -> item));

    return newOrUpdateItems.stream()
            .map(orderItem -> {
              OrderItemPersistenceEntity itemPersistence = existingItemMap.getOrDefault(
                      orderItem.id().value().toLong(), new OrderItemPersistenceEntity()
              );
              return merge(itemPersistence, orderItem);
    }).collect(Collectors.toSet());
  }

  public OrderItemPersistenceEntity fromDomain(OrderItem orderItem) {
    return merge(new OrderItemPersistenceEntity(), orderItem);
  }

  private OrderItemPersistenceEntity merge(OrderItemPersistenceEntity entity, OrderItem orderItem) {
    entity.setId(orderItem.id().value().toLong());
    entity.setProductId(orderItem.productId().value());
    entity.setProductName(orderItem.productName().value());
    entity.setPrice(orderItem.price().value());
    entity.setQuantity(orderItem.quantity().value());
    entity.setTotalAmount(orderItem.totalAmount().value());
    return entity;
  }

  private BillingEmbeddable toBillingEmbeddable(Billing billing) {
    return BillingEmbeddable.builder()
            .firstName(billing.fullName().firstName())
            .lastName(billing.fullName().lastName())
            .document(billing.document().value())
            .phone(billing.phone().value())
            .email(billing.email().value())
            .address(AddressEmbeddable.builder()
                    .street(billing.address().street())
                    .number(billing.address().number())
                    .complement(billing.address().complement())
                    .neighborhood(billing.address().neighborhood())
                    .city(billing.address().city())
                    .state(billing.address().state())
                    .zipCode(billing.address().zipCode().value())
                    .build()).build();
  }

  private ShippingEmbeddable toShippingEmbeddable(Shipping shipping) {
    return ShippingEmbeddable.builder()
            .cost(shipping.cost().value())
            .expectedDate(shipping.expectedDate())
            .recipient(RecipientEmbeddable.builder()
                    .firstName(shipping.recipient().fullName().firstName())
                    .lastName(shipping.recipient().fullName().lastName())
                    .document(shipping.recipient().document().value())
                    .phone(shipping.recipient().phone().value())
                    .build())
            .address(AddressEmbeddable.builder()
                    .street(shipping.address().street())
                    .number(shipping.address().number())
                    .complement(shipping.address().complement())
                    .neighborhood(shipping.address().neighborhood())
                    .city(shipping.address().city())
                    .state(shipping.address().state())
                    .zipCode(shipping.address().zipCode().value())
                    .build()).build();
  }

}
