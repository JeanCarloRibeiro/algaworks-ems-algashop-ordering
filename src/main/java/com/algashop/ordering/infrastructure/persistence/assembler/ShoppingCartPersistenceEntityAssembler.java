package com.algashop.ordering.infrastructure.persistence.assembler;

import com.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class ShoppingCartPersistenceEntityAssembler {

  private final CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

  public ShoppingCartPersistenceEntity fromDomain(ShoppingCart shoppingCart) {
    return merge(new ShoppingCartPersistenceEntity(), shoppingCart);
  }

  public ShoppingCartPersistenceEntity merge(ShoppingCartPersistenceEntity entity, ShoppingCart shoppingCart) {
    entity.setId(shoppingCart.id().value());
    entity.setCustomer(getCustomer(shoppingCart));
    entity.setTotalAmount(shoppingCart.totalAmount().value());
    entity.setTotalItems(shoppingCart.totalItems().value());
    entity.setCreatedAt(shoppingCart.createdAt());
    entity.setItems(new HashSet<>());
    entity.setVersion(shoppingCart.version());
    return entity;
  }

  private CustomerPersistenceEntity getCustomer(ShoppingCart shoppingCart) {
    return customerPersistenceEntityRepository.getReferenceById(shoppingCart.customerId().value());
  }

}
