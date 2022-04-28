package pl.catalyst.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.catalyst.model.customer.Customer;
import pl.catalyst.model.customer.mappers.CustomerCategoryView;
import pl.catalyst.model.customer.repository.CustomerRepository;
import pl.catalyst.repository.exception.CustomerRepositoryException;
import pl.catalyst.utils.converter.CustomerCategoryMapperConverter;
import pl.catalyst.model.customer.converter.CustomerJsonConverter;
import pl.catalyst.utils.properties.ConfigProperties;

import java.util.List;

@AllArgsConstructor
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final ConfigProperties config;

    @Override
    public List<Customer> getCustomer() {
        var customer = config.getConfigValue("customer-filename");
        return new CustomerJsonConverter()
                .fromJson(customer)
                .orElseThrow(() -> new CustomerRepositoryException("Cannot get users from file"));

    }

    @Override
    public List<CustomerCategoryView> getCategoryMapper() {
        var mapper = config.getConfigValue("category-map-filename");
        return new CustomerCategoryMapperConverter()
                .fromJson(mapper)
                .orElseThrow(() -> new CustomerRepositoryException("Cannot get category mapper"));

    }
}
