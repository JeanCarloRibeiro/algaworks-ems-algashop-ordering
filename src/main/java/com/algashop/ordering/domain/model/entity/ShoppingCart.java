package com.algashop.ordering.domain.model.entity;

import com.algashop.ordering.domain.model.exception.ShoppingCartDoesNotContainItemException;
import com.algashop.ordering.domain.model.valueobject.Product;
import com.algashop.ordering.domain.model.valueobject.Money;
import com.algashop.ordering.domain.model.valueobject.Quantity;
import com.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import com.algashop.ordering.domain.model.valueobject.id.ShoppingCartItemId;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.algashop.ordering.domain.model.exception.ErrorMessages.VALIDATION_ERROR_QUANTITY_MUST_POSITIVE;

public class ShoppingCart {

  private ShoppingCartId id;
  private CustomerId customerId;
  private Money totalAmount;
  private Quantity totalItems;
  private OffsetDateTime createdAt;

  private Set<ShoppingCartItem> items;

  @Builder(builderClassName = "existingShoppingCartBuilder", builderMethodName = "existing")
  private ShoppingCart(ShoppingCartId id, CustomerId customerId, Money totalAmount,
                       Quantity totalItems, OffsetDateTime createdAt,
                       Set<ShoppingCartItem> items) {
    this.setId(id);
    this.setCustomerId(customerId);
    this.setTotalAmount(totalAmount);
    this.setTotalItems(totalItems);
    this.setCreatedAt(createdAt);
    this.setItems(items);
  }

  @Builder(toBuilder = true)
  public static ShoppingCart startShopping(CustomerId customerId) {
    return new ShoppingCart(
            new ShoppingCartId(),
            customerId,
            Money.ZERO,
            Quantity.ZERO,
            OffsetDateTime.now(),
            new HashSet<>()
    );
  }

  public void addItem(Product product, Quantity quantity) {
    Objects.requireNonNull(product);
    Objects.requireNonNull(quantity);

    product.checkOutOfStock();

    try {
      ShoppingCartItem shoppingCartItem = findShoppingCartItem(product);
      this.changeItemQuantity(shoppingCartItem.id(), shoppingCartItem.quantity());
      shoppingCartItem.refresh(product);
    } catch (ShoppingCartDoesNotContainItemException e) {
      ShoppingCartItem cartItem = ShoppingCartItem.brandNew()
              .shoppingCartId(this.id())
              .product(product)
              .quantity(quantity)
              .available(true)
              .build();

      if (items == null) {
        items = new HashSet<>();
      }
      this.items.add(cartItem);
    }
    this.recalculateTotals();
  }

  public void refreshItem(Product product) {
    ShoppingCartItem cartItem = this.findItem(product);
    cartItem.refresh(product);
    this.recalculateTotals();
  }

  public void removeItem(ShoppingCartItemId cartItemId) {
    ShoppingCartItem cartItem = findShoppingCartItem(cartItemId);
    this.items.remove(cartItem);
    this.recalculateTotals();
  }

  public void empty() {
    this.items.clear();
    this.recalculateTotals();
  }

  public ShoppingCartItem findItem(ShoppingCartItemId id) {
    return findShoppingCartItem(id);
  }

  public ShoppingCartItem findItem(Product product) {
    return findShoppingCartItem(product);
  }

  public void changeItemQuantity(ShoppingCartItemId id, Quantity quantity) {
    Objects.requireNonNull(id);
    Objects.requireNonNull(quantity);

    if (quantity.value() <= 0) {
      throw new IllegalArgumentException(VALIDATION_ERROR_QUANTITY_MUST_POSITIVE);
    }
    ShoppingCartItem shoppingCartItem = findShoppingCartItem(id);
    shoppingCartItem.changeQuantity(quantity);

    this.recalculateTotals();
  }

  public Boolean containsUnavailableItems() {
    return this.items().stream().iterator().next().available().equals(false);
  }

  public ShoppingCartId id() {
    return id;
  }

  public CustomerId customerId() {
    return customerId;
  }

  public Money totalAmount() {
    return totalAmount;
  }

  public Quantity totalItems() {
    return totalItems;
  }

  public OffsetDateTime createdAt() {
    return createdAt;
  }

  public Set<ShoppingCartItem> items() {
    return Collections.unmodifiableSet(items);
  }

  public Boolean isEmpty() {
    return this.items().isEmpty();
  }

  private void recalculateTotals() {
    BigDecimal totalItemsAmount = this.items().stream().map(i -> i.totalAmount().value())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    Integer totalItemsQuantity = this.items().stream().map(i -> i.quantity().value())
            .reduce(0, Integer::sum);

    this.setTotalAmount(new Money(totalItemsAmount));
    this.setTotalItems(new Quantity(totalItemsQuantity));
  }

  private ShoppingCartItem findShoppingCartItem(ShoppingCartItemId id) {
    return this.items().stream().filter(i -> i.id().equals(id)).findFirst()
            .orElseThrow(() -> new ShoppingCartDoesNotContainItemException(this.id()));
  }

  private ShoppingCartItem findShoppingCartItem(Product product) {
    return this.items().stream().filter(i -> i.product().id().equals(product.id())).findFirst()
            .orElseThrow(() -> new ShoppingCartDoesNotContainItemException(this.id()));
  }

  private void setId(ShoppingCartId id) {
    Objects.requireNonNull(id);
    this.id = id;
  }

  private void setCustomerId(CustomerId customerId) {
    Objects.requireNonNull(customerId);
    this.customerId = customerId;
  }

  private void setTotalAmount(Money totalAmount) {
    Objects.requireNonNull(totalAmount);
    this.totalAmount = totalAmount;
  }

  private void setTotalItems(Quantity totalItems) {
    Objects.requireNonNull(totalItems);
    this.totalItems = totalItems;
  }

  private void setCreatedAt(OffsetDateTime createdAt) {
    Objects.requireNonNull(createdAt);
    this.createdAt = createdAt;
  }

  public void setItems(Set<ShoppingCartItem> items) {
    Objects.requireNonNull(items);
    this.items = items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ShoppingCart cart)) return false;
    return Objects.equals(id, cart.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
