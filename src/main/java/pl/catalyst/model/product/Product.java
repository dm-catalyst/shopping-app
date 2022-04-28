package pl.catalyst.model.product;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import pl.catalyst.model.product.dto.GetProductDto;

import java.math.BigDecimal;


@Builder
@EqualsAndHashCode(exclude = "quantity")
public class Product {
    String name;
    Integer quantity;
    BigDecimal price;
    String category;

    /**
     * @param value - category to check
     * @return True for category product match or false for not match
     */

    public boolean checkCategory(String value) {
        return category.equalsIgnoreCase(value);
    }

    /**
     * @return True when product is available or false when not available
     */

    public boolean checkQuantity() {
        return quantity > 0;
    }

    /**
     * @return True when product reduced quantity or false when not reduced
     */

    public boolean reduceQuantity() {
        if (this.quantity != 0) {
            this.quantity = quantity - 1;
            return true;
        }
        return false;
    }

    /**
     * @return Product DTO
     */

    public GetProductDto toGetProductDto() {
        return GetProductDto.builder()
                .name(name)
                .quantity(quantity)
                .price(price)
                .category(category)
                .build();
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", cash=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}
