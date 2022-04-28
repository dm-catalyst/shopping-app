package pl.catalyst.model.customer.mappers;

import pl.catalyst.config.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class CategoryValidator implements Validator<CustomerCategoryView> {

    private Map<String, String> errors;

    @Override
    public Map<String, String> validate(CustomerCategoryView customerCategoryView) {
        this.errors = new HashMap<>();

        if (customerCategoryView == null || customerCategoryView.symbol == null || customerCategoryView.description == null) {
            errors.put("CustomerCategory object", "is null");
            return errors;
        }

        if (!customerCategoryView.description.matches("[A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż]*")) {
            errors.put("CustomerCategoryView description", "is incorrect: " + customerCategoryView.description);
        }

        if (!customerCategoryView.symbol.matches("[0-9]*")) {
            errors.put("CustomerCategoryView symbol", "is incorrect: " + customerCategoryView.symbol);
        }
        return errors;
    }
}
