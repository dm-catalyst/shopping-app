package pl.catalyst.util;

import pl.catalyst.model.customer.Customer;

import java.math.BigDecimal;

public interface CustomerFactory {
    Customer customer1 = Customer.builder().name("Name").surname("Surname").age(33).cash(new BigDecimal(8000)).preferences("123").build();
    Customer customer2 = Customer.builder().name("Namea").surname("Surnamea").age(33).cash(new BigDecimal(800)).preferences("2").build();
    Customer customer3 = Customer.builder().name("Nameb").surname("Surnameb").age(33).cash(new BigDecimal(800)).preferences("31").build();

    Customer customer = Customer.builder().name("Nameb").surname("Surnameb").age(33).cash(new BigDecimal(800)).preferences("31").build();
}
