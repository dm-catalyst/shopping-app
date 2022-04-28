package pl.catalyst.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.catalyst.model.customer.dto.GetCustomerDto;
import pl.catalyst.model.product.ProductUtils;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.catalyst.util.CustomerFactory.customer1;
import static pl.catalyst.util.CustomerFactory.customer3;
import static pl.catalyst.util.ProductFactory.productAGD;
import static pl.catalyst.util.ProductFactory.productRTV;

public class CustomerModelTest {

    @Test
    @DisplayName("When conversion to GetCustomerDto is correct")
    public void test1() {
        GetCustomerDto getCustomerDto = customer1.toGetCustomerDto();

        String name = "Name";
        String surname = "Surname";
        Integer age = 33;
        BigDecimal price = new BigDecimal(8000);
        String preferences = "123";

        GetCustomerDto expectedCustomer = GetCustomerDto
                .builder()
                .name(name)
                .surname(surname)
                .age(age)
                .cash(price)
                .preferences(preferences)
                .build();

        assertThat(getCustomerDto).isEqualTo(expectedCustomer);
    }

    @Test
    @DisplayName("When get customer preferences is correct")
    public void test2() {
        assertThat(customer1.checkPreferences("1")).isTrue();
        assertThat(customer1.checkPreferences("2")).isTrue();
        assertThat(customer1.checkPreferences("3")).isTrue();
    }

    @Test
    @DisplayName("When get customer preferences is incorrect")
    public void test3() {
        assertThat(customer1.checkPreferences("8")).isFalse();
        assertThat(customer1.checkPreferences("10")).isFalse();
        assertThat(customer1.checkPreferences("a")).isFalse();
    }

    @Test
    @DisplayName("When get customer preferences is incorrect")
    public void test4() {
        assertThat(customer3.checkPreferences("1")).isTrue();
        assertThat(customer3.checkPreferences("2")).isFalse();
        assertThat(customer3.checkPreferences("3")).isTrue();
    }

    @Test
    @DisplayName("When customer has money for new product")
    public void test5() {
        assertThat(customer1.checkHasMoney(ProductUtils.toPrice.apply(productAGD))).isTrue();
        assertThat(customer1.checkHasMoney(ProductUtils.toPrice.apply(productRTV))).isTrue();
    }

    @Test
    @DisplayName("When customer has not money for new product")
    public void test6() {
        assertThat(customer3.checkHasMoney(ProductUtils.toPrice.apply(productAGD))).isFalse();
    }

    @Test
    @DisplayName("When customer have money and buy product")
    public void test7() {
        assertThat(customer1.reduceCash(ProductUtils.toPrice.apply(productAGD))).isTrue();
    }

    @Test
    @DisplayName("When customer have not money and buy product")
    public void test8() {
        assertThat(customer3.reduceCash(ProductUtils.toPrice.apply(productAGD))).isFalse();
    }
}
