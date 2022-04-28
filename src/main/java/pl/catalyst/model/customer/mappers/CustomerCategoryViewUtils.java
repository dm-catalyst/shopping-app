package pl.catalyst.model.customer.mappers;

import java.util.function.Function;

public interface CustomerCategoryViewUtils {
    Function<CustomerCategoryView, String> toDescription = mapper -> mapper.description;
}
