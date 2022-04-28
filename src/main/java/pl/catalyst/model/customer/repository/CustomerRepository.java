package pl.catalyst.model.customer.repository;

import pl.catalyst.model.customer.Customer;
import pl.catalyst.model.customer.mappers.CustomerCategoryView;

import java.util.List;

public interface CustomerRepository {
    List<Customer> getCustomer();
    List<CustomerCategoryView> getCategoryMapper();
}
