package pl.catalyst.model.product;

import java.math.BigDecimal;
import java.util.function.Function;

public interface ProductUtils {
    Function<Product, BigDecimal> toPrice = product -> product.price;
    Function<Product, String> toProductName = product -> product.name;
    Function<Product, String> toProductCategory = product -> product.category;
}
