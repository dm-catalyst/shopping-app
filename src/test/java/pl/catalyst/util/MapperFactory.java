package pl.catalyst.util;

import pl.catalyst.model.customer.mappers.CustomerCategoryView;

import java.util.List;

public interface MapperFactory {
    CustomerCategoryView mapper1 = CustomerCategoryView.builder().symbol("1").description("AGD").build();
    CustomerCategoryView mapper2 = CustomerCategoryView.builder().symbol("2").description("RTV").build();
    CustomerCategoryView mapper3 = CustomerCategoryView.builder().symbol("3").description("Clothes").build();
    List<CustomerCategoryView> mappers = List.of(mapper1, mapper2, mapper3);
}
