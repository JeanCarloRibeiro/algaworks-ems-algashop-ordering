package com.algashop.ordering.domain.model.entity;

import com.algashop.ordering.domain.model.exception.ShoppingCartItemIncompatibleProductException;
import com.algashop.ordering.domain.model.valueobject.Money;
import com.algashop.ordering.domain.model.valueobject.Product;
import com.algashop.ordering.domain.model.valueobject.Quantity;
import com.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import com.algashop.ordering.domain.model.valueobject.id.ShoppingCartItemId;
import lombok.Builder;

import java.util.Objects;

import static com.algashop.ordering.domain.model.exception.ErrorMessages.VALIDATION_ERROR_QUANTITY_MUST_POSITIVE;

public class ShoppingCartItem implements AggregateRoot<ShoppingCartItemId> {

  private ShoppingCartItemId id;
  private ShoppingCartId shoppingCartId;
  private Product product;
  private Quantity quantity;
  private Money totalAmount;
  private Boolean available;

  public ShoppingCartItemId id() {
    return id;
  }

  public ShoppingCartId shoppingCartId() {
    return shoppingCartId;
  }

  public Product product() {
    return product;
  }

  public Quantity quantity() {
    return quantity;
  }

  public Money totalAmount() {
    return totalAmount;
  }

  public Boolean available() {
    return this.available;
  }

  @Builder(builderClassName = "ExistingShoppingCartItem", builderMethodName = "existing")
  private ShoppingCartItem(
          ShoppingCartItemId id, ShoppingCartId shoppingCartId,
          Product product, Quantity quantity, Money totalAmount,
          Boolean available) {
    this.setId(id);
    this.setShoppingCartId(shoppingCartId);
    this.setProduct(product);
    this.setQuantity(quantity);
    this.setTotalAmount(totalAmount);
    this.setAvailable(available);
  }

  @Builder(builderClassName = "BrandNewShoppingCartItem", builderMethodName = "brandNew")
  public static ShoppingCartItem createBrandNew(
          ShoppingCartId shoppingCartId, Product product, Quantity quantity, Boolean available) {

    Objects.requireNonNull(shoppingCartId);
    Objects.requireNonNull(product);
    Objects.requireNonNull(quantity);
    Objects.requireNonNull(available);

    ShoppingCartItem shopping = new ShoppingCartItem(
            new ShoppingCartItemId(),
            shoppingCartId,
            product,
            quantity,
            Money.ZERO,
            available
    );
    shopping.recalculateTotals();
    return shopping;
  }

  void changeQuantity(Quantity quantity) {
    Objects.requireNonNull(quantity);

    if (quantity.value() <= 0) {
      throw new IllegalArgumentException(VALIDATION_ERROR_QUANTITY_MUST_POSITIVE);
    }
    this.setQuantity(quantity);
    this.recalculateTotals();
  }

  void refresh(Product product) {
    Objects.requireNonNull(product);

    if (!this.product().id().equals(product.id())) {
      throw new ShoppingCartItemIncompatibleProductException(this.id());
    }
    this.setProduct(product);
    this.recalculateTotals();
  }

  private void recalculateTotals() {
    this.setTotalAmount(this.product().price().multiply(this.quantity()));
  }

  private void setId(ShoppingCartItemId id) {
    this.id = id;
  }

  private void setShoppingCartId(ShoppingCartId shoppingCartId) {
    this.shoppingCartId = shoppingCartId;
  }

  private void setProduct(Product product) {
    this.product = product;
  }

  public void setQuantity(Quantity quantity) {
    this.quantity = quantity;
  }

  private void setTotalAmount(Money totalAmount) {
    this.totalAmount = totalAmount;
  }

  private void setAvailable(Boolean available) {
    this.available = available;
  }

}
