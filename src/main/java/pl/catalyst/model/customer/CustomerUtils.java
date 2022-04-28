package pl.catalyst.model.customer;

import java.util.function.Function;

public interface CustomerUtils {
    Function<Customer, String> toPreference = customer -> customer.preferences;
}
