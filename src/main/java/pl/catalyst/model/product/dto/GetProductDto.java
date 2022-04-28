package pl.catalyst.model.product.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Builder
@Data
@EqualsAndHashCode(exclude = "quantity")
public class GetProductDto {
    String name;
    Integer quantity;
    BigDecimal price;
    String category;
}
