package pl.catalyst.service.dto;

import lombok.Builder;
import lombok.Data;
import pl.catalyst.model.customer.Customer;
import pl.catalyst.model.product.Product;

import java.util.List;
import java.util.Map;


@Builder
@Data
public class GetShoppingListDto {
    Map<Customer, List<Product>> shopping;
}
