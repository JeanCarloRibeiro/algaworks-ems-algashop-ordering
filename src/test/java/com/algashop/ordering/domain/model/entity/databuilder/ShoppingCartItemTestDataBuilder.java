package com.algashop.ordering.domain.model.entity.databuilder;

import com.algashop.ordering.domain.model.entity.ShoppingCartItem;
import com.algashop.ordering.domain.model.valueobject.Product;
import com.algashop.ordering.domain.model.valueobject.Money;
import com.algashop.ordering.domain.model.valueobject.ProductName;
import com.algashop.ordering.domain.model.valueobject.Quantity;
import com.algashop.ordering.domain.model.valueobject.id.ProductId;
import com.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;

public class ShoppingCartItemTestDataBuilder {

  public static ShoppingCartItem.BrandNewShoppingCartItem shoppingCartItem() {
    return ShoppingCartItem.brandNew()
            .shoppingCartId(new ShoppingCartId())
            .quantity(new Quantity(1))
            .available(true)
            .product(Product.builder()
                    .id(new ProductId())
                    .name(new ProductName("RAM 32GB DDR5"))
                    .price(new Money("1000"))
                    .inStock(true)
                    .build());
  }

  public static ShoppingCartItem.BrandNewShoppingCartItem shoppingCartItemUnavailable() {
    return ShoppingCartItem.brandNew()
            .shoppingCartId(new ShoppingCartId())
            .quantity(new Quantity(1))
            .product(Product.builder()
                    .id(new ProductId())
                    .name(new ProductName("RAM 32GB DDR5"))
                    .price(new Money("1000"))
                    .inStock(true).build())
            .available(false);
  }

}
