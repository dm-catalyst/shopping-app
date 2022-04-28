package pl.catalyst.model.product;

import pl.catalyst.config.validator.Validator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProductValidator implements Validator<Product> {

    Map<String, String> errors = new HashMap<>();

    @Override
    public Map<String, String> validate(Product product) {

        if (product == null || product.name == null || product.category == null || product.price == null || product.quantity == null) {
            errors.put("Product object or product object field", "is null");
            return errors;
        }

        if (!product.name.matches("[A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż]*")) {
            errors.put("Product name", "is incorrect: " + product.name);
        }

        if (!product.category.matches("[A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż]*")) {
            errors.put("Product category", "is incorrect: " + product.category);
        }

        if (product.price.compareTo(BigDecimal.ZERO) < 0) {
            errors.put("Product price", "is incorrect. Wrong value: " + product.price);
        }

        if (product.quantity < 0) {
            errors.put("Product quantity", "quantity cannot be lower then zero");
        }
        return errors;
    }
}
