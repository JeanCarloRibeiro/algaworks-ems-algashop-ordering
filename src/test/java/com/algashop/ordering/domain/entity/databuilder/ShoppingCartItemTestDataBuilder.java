package com.algashop.ordering.domain.entity.databuilder;

import com.algashop.ordering.domain.entity.ShoppingCartItem;
import com.algashop.ordering.domain.valueobject.Money;
import com.algashop.ordering.domain.valueobject.Product;
import com.algashop.ordering.domain.valueobject.ProductName;
import com.algashop.ordering.domain.valueobject.Quantity;
import com.algashop.ordering.domain.valueobject.id.ProductId;
import com.algashop.ordering.domain.valueobject.id.ShoppingCartId;

public class ShoppingCartItemTestDataBuilder {

  public static ShoppingCartItem.brandNewShoppingCartItemBuild shoppingCartItem() {
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

  public static ShoppingCartItem.brandNewShoppingCartItemBuild shoppingCartItemUnavailable() {
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
