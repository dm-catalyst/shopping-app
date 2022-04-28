package pl.catalyst.model.customer.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Builder
@Data
@EqualsAndHashCode(exclude = "cash")
public class GetCustomerDto {
    String name;
    String surname;
    Integer age;
    BigDecimal cash;
    String preferences;
}
