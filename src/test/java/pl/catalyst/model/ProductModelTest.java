package pl.catalyst.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.catalyst.model.product.Product;
import pl.catalyst.model.product.dto.GetProductDto;

import java.math.BigDecimal;

import static pl.catalyst.util.ProductFactory.*;

public class ProductModelTest {

    @Test
    @DisplayName("When conversion to GetProductDto is correct")
    public void test1() {
        GetProductDto toGetProductDto = productAGD.toGetProductDto();

        GetProductDto expectedProductDto = GetProductDto.builder()
                .name("productAGD")
                .quantity(8)
                .price(new BigDecimal(830))
                .category("AGD")
                .build();

        Assertions.assertThat(toGetProductDto).isEqualTo(expectedProductDto);
    }

    @Test
    @DisplayName("When product category is correct")
    public void test2() {
        Assertions.assertThat(productAGD.checkCategory("AGD")).isTrue();
        Assertions.assertThat(productRTV.checkCategory("RTV")).isTrue();
        Assertions.assertThat(productClothes.checkCategory("Clothes")).isTrue();
    }

    @Test
    @DisplayName("When product category is incorrect")
    public void test3() {
        Assertions.assertThat(productAGD.checkCategory("RTV")).isFalse();
        Assertions.assertThat(productAGD.checkCategory("123")).isFalse();
        Assertions.assertThat(productAGD.checkCategory("XYZ")).isFalse();
    }

    @Test
    @DisplayName("When product is available")
    public void test4() {
        Assertions.assertThat(productAGD.checkQuantity()).isTrue();
    }

    @Test
    @DisplayName("When product is unavailable")
    public void test5() {
        var product = Product.builder().name("productAGD").price(new BigDecimal(830)).category("AGD").quantity(0).build();
        Assertions.assertThat(product.checkQuantity()).isFalse();
    }

    @Test
    @DisplayName("When reducing product quantity")
    public void test6() {
        Assertions.assertThat(productAGD.reduceQuantity()).isTrue();
    }

    @Test
    @DisplayName("When reducing the unavailable product")
    public void test7() {
        var product = Product.builder().name("productAGD").price(new BigDecimal(830)).category("AGD").quantity(0).build();
        Assertions.assertThat(product.reduceQuantity()).isFalse();
    }
}
