package pl.catalyst.config.validator;

import pl.catalyst.config.validator.exception.ValidatorException;

import java.util.Map;

import static java.util.stream.Collectors.joining;

public interface Validator<T> {
    Map<String, String> validate(T t);

    static <T> void validate(Validator<T> validator, T t) {
        var errors = validator.validate(t);
        if (!errors.isEmpty()) {
            System.out.println("validator");
            var message = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(joining(", "));
            throw new ValidatorException("[VALIDATION ERRORS]: " + message);
        }
    }
}
