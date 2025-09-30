package com.algashop.ordering.domain.entity.databuilder;

import com.algashop.ordering.domain.valueobject.Money;
import com.algashop.ordering.domain.valueobject.Product;
import com.algashop.ordering.domain.valueobject.ProductName;
import com.algashop.ordering.domain.valueobject.id.ProductId;

public class ProductTestDataBuilder {

  public static Product.ProductBuilder product() {
    return Product.builder()
            .id(new ProductId())
            .name(new ProductName("Notebook X11"))
            .price(new Money("1000"))
            .inStock(true);
  }

  public static Product.ProductBuilder productUnavailable() {
    return Product.builder()
            .id(new ProductId())
            .name(new ProductName("Desktop FX9000"))
            .price(new Money("5000"))
            .inStock(false);
  }

  public static Product.ProductBuilder productAltRamMemory() {
    return Product.builder()
            .id(new ProductId())
            .name(new ProductName("RAM 32GB DDR5"))
            .price(new Money("1000"))
            .inStock(true);
  }

  public static Product.ProductBuilder productAltMousePad() {
    return Product.builder()
            .id(new ProductId())
            .name(new ProductName("Mouse Pad"))
            .price(new Money("100"))
            .inStock(true);
  }

}
