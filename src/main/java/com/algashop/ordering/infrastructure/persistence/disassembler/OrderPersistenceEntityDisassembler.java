package com.algashop.ordering.infrastructure.persistence.disassembler;

import com.algashop.ordering.domain.model.entity.Order;
import com.algashop.ordering.domain.model.entity.OrderItem;
import com.algashop.ordering.domain.model.enums.OrderStatus;
import com.algashop.ordering.domain.model.enums.PaymentMethod;
import com.algashop.ordering.domain.model.valueobject.Address;
import com.algashop.ordering.domain.model.valueobject.Billing;
import com.algashop.ordering.domain.model.valueobject.Document;
import com.algashop.ordering.domain.model.valueobject.Email;
import com.algashop.ordering.domain.model.valueobject.FullName;
import com.algashop.ordering.domain.model.valueobject.Money;
import com.algashop.ordering.domain.model.valueobject.Phone;
import com.algashop.ordering.domain.model.valueobject.ProductName;
import com.algashop.ordering.domain.model.valueobject.Quantity;
import com.algashop.ordering.domain.model.valueobject.Recipient;
import com.algashop.ordering.domain.model.valueobject.Shipping;
import com.algashop.ordering.domain.model.valueobject.ZipCode;
import com.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algashop.ordering.domain.model.valueobject.id.OrderItemId;
import com.algashop.ordering.domain.model.valueobject.id.ProductId;
import com.algashop.ordering.infrastructure.persistence.embeddable.BillingEmbeddable;
import com.algashop.ordering.infrastructure.persistence.embeddable.ShippingEmbeddable;
import com.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderPersistenceEntityDisassembler {
  public Order toDomainEntity(OrderPersistenceEntity orderPersistence) {
    return Order.existing()
            .id(new OrderId(orderPersistence.getId()))
            .customerId(new CustomerId(orderPersistence.getCustomerId()))
            .totalAmount(new Money(orderPersistence.getTotalAmount()))
            .totalItems(new Quantity(orderPersistence.getTotalItems()))
            .status(OrderStatus.valueOf(orderPersistence.getStatus()))
            .paymentMethod(PaymentMethod.valueOf(orderPersistence.getPaymentMethod()))
            .placedAt(orderPersistence.getPlacedAt())
            .paidAt(orderPersistence.getPaidAt())
            .canceledAt(orderPersistence.getCanceledAt())
            .readyAt(orderPersistence.getReadAt())
            .items(new HashSet<>())
            .version(orderPersistence.getVersion())
            .billing(toBilling(orderPersistence.getBilling()))
            .shipping(toShipping(orderPersistence.getShipping()))
//            .billing(Billing.builder()
//                    .fullName(new FullName(orderPersistence.getBilling().getFirstName(), orderPersistence.getBilling().getLastName()))
//                    .document(new Document(orderPersistence.getBilling().getDocument()))
//                    .phone(new Phone(orderPersistence.getBilling().getPhone()))
//                    .email(new Email(orderPersistence.getBilling().getEmail()))
//                    .address(Address.builder()
//                            .street(orderPersistence.getBilling().getAddress().getStreet())
//                            .number(orderPersistence.getBilling().getAddress().getNumber())
//                            .complement(orderPersistence.getBilling().getAddress().getComplement())
//                            .neighborhood(orderPersistence.getBilling().getAddress().getNeighborhood())
//                            .city(orderPersistence.getBilling().getAddress().getCity())
//                            .state(orderPersistence.getBilling().getAddress().getState())
//                            .zipCode(new ZipCode(orderPersistence.getBilling().getAddress().getZipCode()))
//                            .build())
//                    .build())
//            .shipping(Shipping.builder()
//                    .cost(new Money(orderPersistence.getShipping().getCost()))
//                    .expectedDate(orderPersistence.getShipping().getExpectedDate())
//                    .recipient(Recipient.builder()
//                            .fullName(new FullName(
//                                    orderPersistence.getShipping().getRecipient().getFirstName(),
//                                    orderPersistence.getShipping().getRecipient().getLastName()))
//                            .document(new Document(orderPersistence.getShipping().getRecipient().getDocument()))
//                            .phone(new Phone(orderPersistence.getShipping().getRecipient().getPhone()))
//                            .build())
//                    .address(Address.builder()
//                            .street(orderPersistence.getShipping().getAddress().getStreet())
//                            .number(orderPersistence.getShipping().getAddress().getNumber())
//                            .complement(orderPersistence.getShipping().getAddress().getComplement())
//                            .neighborhood(orderPersistence.getShipping().getAddress().getNeighborhood())
//                            .city(orderPersistence.getShipping().getAddress().getCity())
//                            .state(orderPersistence.getShipping().getAddress().getState())
//                            .zipCode(new ZipCode(orderPersistence.getShipping().getAddress().getZipCode()))
//                            .build())
//                    .build())
            .items(mergeItems(orderPersistence.getItems()))
            .build();
  }

  private Billing toBilling(BillingEmbeddable billing) {
    return Billing.builder()
            .fullName(new FullName(billing.getFirstName(), billing.getLastName()))
            .document(new Document(billing.getDocument()))
            .phone(new Phone(billing.getPhone()))
            .email(new Email(billing.getEmail()))
            .address(Address.builder()
                    .street(billing.getAddress().getStreet())
                    .number(billing.getAddress().getNumber())
                    .complement(billing.getAddress().getComplement())
                    .neighborhood(billing.getAddress().getNeighborhood())
                    .city(billing.getAddress().getCity())
                    .state(billing.getAddress().getState())
                    .zipCode(new ZipCode(billing.getAddress().getZipCode()))
                    .build())
            .build();
  }

  private Shipping toShipping(ShippingEmbeddable shipping) {
    return Shipping.builder()
            .cost(new Money(shipping.getCost()))
            .expectedDate(shipping.getExpectedDate())
            .recipient(Recipient.builder()
                    .fullName(new FullName(shipping.getRecipient().getFirstName(), shipping.getRecipient().getLastName()))
                    .document(new Document(shipping.getRecipient().getDocument()))
                    .phone(new Phone(shipping.getRecipient().getPhone()))
                    .build())
            .address(Address.builder()
                    .street(shipping.getAddress().getStreet())
                    .number(shipping.getAddress().getNumber())
                    .complement(shipping.getAddress().getComplement())
                    .neighborhood(shipping.getAddress().getNeighborhood())
                    .city(shipping.getAddress().getCity())
                    .state(shipping.getAddress().getState())
                    .zipCode(new ZipCode(shipping.getAddress().getZipCode()))
                    .build())
            .build();
  }

  private Set<OrderItem> mergeItems(Set<OrderItemPersistenceEntity> items) {
    return items.stream()
            .map(item -> OrderItem.existing()
                    .id(new OrderItemId(item.getId()))
                    .price(new Money(item.getPrice()))
                    .productId(new ProductId(item.getProductId()))
                    .productName(new ProductName(item.getProductName()))
                    .quantity(new Quantity(item.getQuantity()))
                    .totalAmount(new Money(item.getTotalAmount()))
                    .orderId(new OrderId(item.getOrderId()))
                    .build())
            .collect(Collectors.toSet());
  }

}
