package pl.catalyst.util;

import pl.catalyst.model.product.Product;

import java.math.BigDecimal;

public interface ProductFactory {
    Product productAGD = Product.builder().name("productAGD").price(new BigDecimal(830)).category("AGD").quantity(8).build();
    Product productRTV = Product.builder().name("productRTV").price(new BigDecimal(330)).category("RTV").quantity(8).build();
    Product productClothes = Product.builder().name("productClothes").price(new BigDecimal(400)).category("Clothes").quantity(8).build();
}
