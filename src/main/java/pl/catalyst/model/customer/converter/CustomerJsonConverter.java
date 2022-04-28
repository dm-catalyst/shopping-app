package pl.catalyst.model.customer.converter;


import org.springframework.stereotype.Component;
import pl.catalyst.model.customer.Customer;
import pl.catalyst.utils.converter.JsonConverter;

import java.util.List;

@Component
public class CustomerJsonConverter extends JsonConverter<List<Customer>> {
    public CustomerJsonConverter() {
    }
}
