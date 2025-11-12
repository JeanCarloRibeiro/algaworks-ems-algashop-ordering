package com.algashop.ordering.infrastructure.persistence.entity.databuilder;

import com.algashop.ordering.domain.model.utility.IdGenerator;
import com.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.algashop.ordering.infrastructure.persistence.embeddable.BillingEmbeddable;
import com.algashop.ordering.infrastructure.persistence.embeddable.RecipientEmbeddable;
import com.algashop.ordering.infrastructure.persistence.embeddable.ShippingEmbeddable;
import com.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity.OrderPersistenceEntityBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;

public class OrderPersistenceEntityTestDataBuilder {

  private OrderPersistenceEntityTestDataBuilder() {
  }

  public static OrderPersistenceEntityBuilder existingOrder() {
    return OrderPersistenceEntity.builder()
            .id(IdGenerator.generateTSID().toLong())
            .customer(CustomerPersistenceEntityTestDataBuilder.existingCustomer().build())
            .totalItems(2)
            .totalAmount(new BigDecimal(1000))
            .status("DRAFT")
            .paymentMethod("CREDIT_CARD")
            .placedAt(OffsetDateTime.now())
            .items(Set.of(existingItem().build(), existingItemAlt().build()))
            .billing(toBillingEmbeddable().build())
            .shipping(toShippingEmbeddable().build());
  }

  public static OrderItemPersistenceEntity.OrderItemPersistenceEntityBuilder existingItem() {
    return OrderItemPersistenceEntity.builder()
            .id(IdGenerator.generateTSID().toLong())
            .price(new BigDecimal(500))
            .quantity(2)
            .totalAmount(new BigDecimal(1000))
            .productId(IdGenerator.generateTimeBasedUUID())
            .productName("Notebook");
  }

  public static OrderItemPersistenceEntity.OrderItemPersistenceEntityBuilder existingItemAlt() {
    return OrderItemPersistenceEntity.builder()
            .id(IdGenerator.generateTSID().toLong())
            .price(new BigDecimal(250))
            .quantity(1)
            .totalAmount(new BigDecimal(250))
            .productId(IdGenerator.generateTimeBasedUUID())
            .productName("Mouse pad");
  }

  public static BillingEmbeddable.BillingEmbeddableBuilder toBillingEmbeddable() {
    return BillingEmbeddable.builder()
            .firstName("Jean Carlo")
            .lastName("Ribeiro")
            .document("255-08-0578")
            .phone("478-256-2504")
            .email("jean.ribeiro@gmail.com")
            .address(AddressEmbeddable.builder()
                    .street("Bourbon Street")
                    .number("123")
                    .complement(null)
                    .neighborhood("North Ville")
                    .city("New York")
                    .state("South California")
                    .zipCode("12345")
                    .build());
  }

  private static ShippingEmbeddable.ShippingEmbeddableBuilder toShippingEmbeddable() {
    return ShippingEmbeddable.builder()
            .cost(BigDecimal.valueOf(10))
            .expectedDate(LocalDate.now().plusWeeks(1))
            .recipient(RecipientEmbeddable.builder()
                    .firstName("Jean Carlo")
                    .lastName("Ribeiro")
                    .document("255-08-0578")
                    .phone("478-256-2504")
                    .build())
            .address(AddressEmbeddable.builder()
                    .street("Bourbon Street")
                    .number("123")
                    .complement(null)
                    .neighborhood("North Ville")
                    .city("New York")
                    .state("South California")
                    .zipCode("12345")
                    .build());
  }

}
