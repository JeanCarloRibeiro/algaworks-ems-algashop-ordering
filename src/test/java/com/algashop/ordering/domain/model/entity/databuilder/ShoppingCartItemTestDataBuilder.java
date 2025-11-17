package com.algashop.ordering.domain.model.entity.databuilder;

import com.algashop.ordering.domain.model.entity.ShoppingCartItem;
import com.algashop.ordering.domain.model.valueobject.Money;
import com.algashop.ordering.domain.model.valueobject.Product;
import com.algashop.ordering.domain.model.valueobject.ProductName;
import com.algashop.ordering.domain.model.valueobject.Quantity;
import com.algashop.ordering.domain.model.valueobject.id.ProductId;
import com.algashop.ordering.domain.model.valueobject.id.ShoppingCartItemId;

import static com.algashop.ordering.domain.model.entity.databuilder.ShoppingCartTestDataBuilder.DEFAULT_SHOPPING_CART_ID;

public class ShoppingCartItemTestDataBuilder {
  public static final ShoppingCartItemId DEFAULT_SHOPPING_CART_ITEM_ID = new ShoppingCartItemId();

  public static ShoppingCartItem.ExistingShoppingCartItem shoppingCartItem() {
    return ShoppingCartItem.existing()
            .id(DEFAULT_SHOPPING_CART_ITEM_ID)
            .shoppingCartId(DEFAULT_SHOPPING_CART_ID)
            .quantity(new Quantity(1))
            .available(true)
            .totalAmount(new Money("1000"))
            .product(Product.builder()
                    .id(new ProductId())
                    .name(new ProductName("RAM 32GB DDR5"))
                    .price(new Money("1000"))
                    .inStock(true)
                    .build());
  }

  public static ShoppingCartItem.ExistingShoppingCartItem shoppingCartItemUnavailable() {
    return ShoppingCartItem.existing()
            .id(DEFAULT_SHOPPING_CART_ITEM_ID)
            .shoppingCartId(DEFAULT_SHOPPING_CART_ID)
            .quantity(new Quantity(1))
            .product(Product.builder()
                    .id(new ProductId())
                    .name(new ProductName("RAM 32GB DDR5"))
                    .price(new Money("1000"))
                    .inStock(true).build())
            .available(false);
  }

}
