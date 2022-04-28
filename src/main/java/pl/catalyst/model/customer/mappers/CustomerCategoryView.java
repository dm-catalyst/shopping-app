package pl.catalyst.model.customer.mappers;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public class CustomerCategoryView {
    String symbol;
    String description;
}
