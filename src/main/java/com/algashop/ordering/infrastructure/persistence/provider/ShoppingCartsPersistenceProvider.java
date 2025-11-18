package com.algashop.ordering.infrastructure.persistence.provider;

import com.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algashop.ordering.domain.model.repository.ShoppingCarts;
import com.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import com.algashop.ordering.infrastructure.persistence.assembler.ShoppingCartPersistenceEntityAssembler;
import com.algashop.ordering.infrastructure.persistence.disassembler.ShoppingCartPersistenceEntityDisassembler;
import com.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.repository.ShoppingCartPersistenceEntityRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ShoppingCartsPersistenceProvider implements ShoppingCarts {

  private final ShoppingCartPersistenceEntityRepository persistenceRepository;
  private final ShoppingCartPersistenceEntityAssembler assembler;
  private final ShoppingCartPersistenceEntityDisassembler disassembler;

  private final EntityManager entityManager;

  @Override
  public Optional<ShoppingCart> ofId(ShoppingCartId shoppingCartId) {
    Optional<ShoppingCartPersistenceEntity> result = persistenceRepository.findById(shoppingCartId.value());
    return result.map(disassembler::toDomainEntity);
  }

  @Override
  public boolean exists(ShoppingCartId shoppingCartId) {
    return persistenceRepository.existsById(shoppingCartId.value());
  }

  @Override
  public void add(ShoppingCart aggregateRoot) {
    UUID shoppingCartId = aggregateRoot.id().value();
    persistenceRepository.findById(shoppingCartId)
            .ifPresentOrElse(
                    (persistenceEntity) -> update(aggregateRoot, persistenceEntity),
                    () -> insert(aggregateRoot)
            );
  }

  private void update(ShoppingCart aggregateRoot, ShoppingCartPersistenceEntity persistenceEntity) {
    persistenceEntity = assembler.merge(persistenceEntity, aggregateRoot);
    entityManager.detach(persistenceEntity);
    persistenceEntity = persistenceRepository.saveAndFlush(persistenceEntity);
    updateVersion(aggregateRoot, persistenceEntity);
  }

  private void insert(ShoppingCart aggregateRoot) {
    ShoppingCartPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot);
    persistenceRepository.saveAndFlush(persistenceEntity);
    updateVersion(aggregateRoot, persistenceEntity);
  }

  @SneakyThrows
  private void updateVersion(ShoppingCart aggregateRoot, ShoppingCartPersistenceEntity persistenceEntity) {
    Field version = aggregateRoot.getClass().getDeclaredField("version");
    version.setAccessible(true);
    ReflectionUtils.setField(version, aggregateRoot, persistenceEntity.getVersion());
    version.setAccessible(false);
  }

  @Override
  public long count() {
    return persistenceRepository.count();
  }


  @Override
  public Optional<ShoppingCart> ofCustomer(CustomerId customerId) {
    return persistenceRepository.findByCustomer_Id(customerId.value()).map(disassembler::toDomainEntity);
  }

  @Override
  public void remove(ShoppingCart shoppingCart) {
    boolean present = persistenceRepository.findById(shoppingCart.id().value()).isPresent();
    if (present) {
      remove(shoppingCart.id());
    }
  }

  @Override
  public void remove(ShoppingCartId shoppingCartId) {
    persistenceRepository.deleteById(shoppingCartId.value());
  }
}
