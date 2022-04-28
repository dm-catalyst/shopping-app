package pl.catalyst.model.customer;

import pl.catalyst.config.validator.Validator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CustomerValidator implements Validator<Customer> {

    private Map<String, String> errors;

    @Override
    public Map<String, String> validate(Customer customer) {
        this.errors = new HashMap<>();

        if (customer == null || customer.name == null || customer.surname == null || customer.preferences == null || customer.cash == null || customer.age == null) {
            errors.put("Customer object or object filed component", "is null");
            return errors;
        }

        if (!customer.name.matches("[A-ZĄĆĘŁŃÓŚŹŻ][a-zząćęłńóśźż]*")) {
            errors.put("Customer name", "Incorrect customer name: " + customer.name);
        }

        if (!customer.surname.matches("[A-ZĄĆĘŁŃÓŚŹŻ][a-zząćęłńóśźż]*")) {
            errors.put("Customer name", "Incorrect customer surname: " + customer.surname);
        }

        if (customer.cash.compareTo(BigDecimal.ZERO) < 0) {
            errors.put("Customer price", "Incorrect customer cash: " + customer.cash);
        }

        if (customer.age <= 0) {
            errors.put("Customer age", "Age cannot lower than 1");
        }

        if (!customer.preferences.matches("[0-9][1-9]*")) {
            errors.put("Customer preference", "Preference must be a number. Wrong value: " + customer.preferences);
        }
        return errors;
    }
}
