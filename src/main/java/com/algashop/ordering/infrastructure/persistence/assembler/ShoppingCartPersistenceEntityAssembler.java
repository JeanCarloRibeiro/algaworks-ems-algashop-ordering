package com.algashop.ordering.infrastructure.persistence.assembler;

import com.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class ShoppingCartPersistenceEntityAssembler {

  public ShoppingCartPersistenceEntity fromDomain(ShoppingCart shoppingCart) {
    return merge(new ShoppingCartPersistenceEntity(), shoppingCart);
  }

  public ShoppingCartPersistenceEntity merge(ShoppingCartPersistenceEntity entity, ShoppingCart shoppingCart) {
    entity.setId(shoppingCart.id().value());
    entity.setCustomerId(shoppingCart.customerId().value());
    entity.setTotalAmount(shoppingCart.totalAmount().value());
    entity.setTotalItems(shoppingCart.totalItems().value());
    entity.setCreatedAt(shoppingCart.createdAt());
    entity.setItems(new HashSet<>());
    entity.setVersion(shoppingCart.version());
    return entity;
  }

}
