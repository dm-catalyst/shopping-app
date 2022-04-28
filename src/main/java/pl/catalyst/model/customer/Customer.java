package pl.catalyst.model.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.catalyst.model.customer.dto.GetCustomerDto;

import java.math.BigDecimal;

@Builder
@ToString
@AllArgsConstructor
@EqualsAndHashCode(exclude = "cash")
public class Customer {
    String name;
    String surname;
    Integer age;
    BigDecimal cash;
    String preferences;

    /**
     * @param preference - customer code preference
     * @return Information whether the user has the given preferences
     */

    public boolean checkPreferences(String preference) {
        return preferences.contains(preference);
    }

    /**
     * @param money - amount to be checked
     * @return Information whether the user has money
     */

    public boolean checkHasMoney(BigDecimal money) {
        return cash.compareTo(money) >= 0;
    }

    /**
     * @param money - amount to be reduced
     * @return True when cash reduced or false when cannot reduce
     */

    public boolean reduceCash(BigDecimal money) {
        if (cash.compareTo(money) >= 0) {
            this.cash = cash.subtract(money);
            return true;
        }
        return false;
    }

    /**
     * @return Customer DTO
     */

    public GetCustomerDto toGetCustomerDto() {
        return GetCustomerDto.builder()
                .name(name)
                .surname(surname)
                .age(age)
                .cash(cash)
                .preferences(preferences)
                .build();
    }
}
