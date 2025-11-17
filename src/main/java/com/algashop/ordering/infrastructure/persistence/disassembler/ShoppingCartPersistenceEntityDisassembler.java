package com.algashop.ordering.infrastructure.persistence.disassembler;

import com.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algashop.ordering.domain.model.entity.ShoppingCartItem;
import com.algashop.ordering.domain.model.valueobject.Money;
import com.algashop.ordering.domain.model.valueobject.Product;
import com.algashop.ordering.domain.model.valueobject.ProductName;
import com.algashop.ordering.domain.model.valueobject.Quantity;
import com.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algashop.ordering.domain.model.valueobject.id.ProductId;
import com.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import com.algashop.ordering.domain.model.valueobject.id.ShoppingCartItemId;
import com.algashop.ordering.infrastructure.persistence.entity.ShoppingCartItemPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ShoppingCartPersistenceEntityDisassembler {

  public ShoppingCart toDomainEntity(ShoppingCartPersistenceEntity entity) {
    return ShoppingCart.existing()
            .id(new ShoppingCartId(entity.getId()))
            .customerId(new CustomerId(entity.getCustomerId()))
            .totalAmount(new Money(entity.getTotalAmount()))
            .createdAt(entity.getCreatedAt())
            .items(toShoppingCartItem(entity.getItems()))
            .totalItems(new Quantity(entity.getTotalItems()))
            .build();
  }

  private Set<ShoppingCartItem> toShoppingCartItem(Set<ShoppingCartItemPersistenceEntity> items) {
    return items.stream()
            .map(item -> ShoppingCartItem.existing()
                    .id(new ShoppingCartItemId(item.getId()))
                    .shoppingCartId(new ShoppingCartId(item.getShoppingCart().getId()))
                    .product(Product.builder()
                            .id(new ProductId(item.getProductId()))
                            .name(new ProductName(item.getProductName()))
                            .price(new Money(item.getPrice()))
                            .inStock(item.getInStock())
                            .build())
                    .quantity(new Quantity(item.getQuantity()))
                    .totalAmount(new Money(item.getTotalAmount()))
                    .build())
            .collect(Collectors.toSet());
  }

}
