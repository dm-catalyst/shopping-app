package pl.catalyst.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.catalyst.model.customer.Customer;
import pl.catalyst.model.product.Product;
import pl.catalyst.model.customer.repository.CustomerRepository;
import pl.catalyst.model.product.repository.ProductRepository;
import pl.catalyst.service.dto.ProductCategoryPopularityDto;
import pl.catalyst.service.dto.ProductQuantityDto;
import pl.catalyst.util.CustomerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static pl.catalyst.util.CustomerFactory.*;
import static pl.catalyst.util.CustomerFactory.customer3;
import static pl.catalyst.util.MapperFactory.mappers;
import static pl.catalyst.util.ProductFactory.*;
import static pl.catalyst.util.ProductFactory.productClothes;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ShoppingServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private ShoppingService shoppingService;

    List<Customer> customers;
    List<Product> products;

    @BeforeEach
    private void init() {
        Customer customer1 = Customer.builder().name("Name").surname("Surname").age(33).cash(new BigDecimal(8000)).preferences("123").build();
        Customer customer2 = Customer.builder().name("Namea").surname("Surnamea").age(33).cash(new BigDecimal(800)).preferences("2").build();
        Customer customer3 = Customer.builder().name("Nameb").surname("Surnameb").age(33).cash(new BigDecimal(800)).preferences("31").build();
        Product productAGD = Product.builder().name("productAGD").price(new BigDecimal(830)).category("AGD").quantity(8).build();
        Product productRTV = Product.builder().name("productRTV").price(new BigDecimal(330)).category("RTV").quantity(8).build();
        Product productClothes = Product.builder().name("productClothes").price(new BigDecimal(400)).category("Clothes").quantity(8).build();
        this.customers = Arrays.asList(customer1, customer2, customer3);
        this.products = Arrays.asList(productAGD, productRTV, productClothes);
    }

    @Test
    @DisplayName("When shopping list init correctly")
    public void test1() {
        when(customerRepository.getCustomer())
                .thenReturn(customers);
        when(productRepository.getProduct())
                .thenReturn(products);
        when(customerRepository.getCategoryMapper())
                .thenReturn(mappers);

        System.out.println("test1: " + customer);
        var cus = customer.reduceCash(new BigDecimal(400));
        System.out.println("test1 reduce cash: " +customer);
        var shoppingList = shoppingService.init();
        var rtv = productRTV;
        var clothes = productClothes;
        System.out.println(CustomerFactory.customer1);
        assertThat(shoppingList.get(customer1)).isEqualTo(List.of(productAGD, rtv, clothes));
        assertThat(shoppingList.get(customer2)).isEqualTo(List.of(rtv));
        assertThat(shoppingList.get(customer3)).isEqualTo(List.of(clothes));
    }

    @Test
    @DisplayName("When shopping without customer preference")
    public void test2() {
        System.out.println("test2: " + customer);
        var customer1 = Customer.builder().name("Name").surname("Surname").age(33).cash(new BigDecimal(8000)).preferences("8").build();
        var customer2 = Customer.builder().name("Namea").surname("Surnamea").age(33).cash(new BigDecimal(800)).preferences("88").build();
        var customer3 = Customer.builder().name("Nameb").surname("Surnameb").age(33).cash(new BigDecimal(800)).preferences("0").build();
        var customersWithoutPreference = List.of(customer1, customer2, customer3);
        when(customerRepository.getCustomer())
                .thenReturn(customersWithoutPreference);
        when(productRepository.getProduct())
                .thenReturn(products);
        when(customerRepository.getCategoryMapper())
                .thenReturn(mappers);

        var shoppingList = shoppingService.init();
        assertThat(shoppingList.get(customer1)).isEqualTo(List.of());
        assertThat(shoppingList.get(customer2)).isEqualTo(List.of());
        assertThat(shoppingList.get(customer3)).isEqualTo(List.of());
    }

    @Test
    @DisplayName("When shopping without product quantity")
    public void test3() {
        var productAGD = Product.builder().name("productAGD").price(new BigDecimal(300)).category("AGD").quantity(0).build();
        var productRTV = Product.builder().name("productRTV").price(new BigDecimal(330)).category("RTV").quantity(0).build();
        var productClothes = Product.builder().name("productClothes").price(new BigDecimal(400)).category("Ubrania").quantity(0).build();
        var products = List.of(productAGD, productRTV, productClothes);

        when(customerRepository.getCustomer())
                .thenReturn(customers);
        when(productRepository.getProduct())
                .thenReturn(products);
        when(customerRepository.getCategoryMapper())
                .thenReturn(mappers);

        var shoppingList = shoppingService.init();
        assertThat(shoppingList.get(customer1)).isEqualTo(List.of());
        assertThat(shoppingList.get(customer2)).isEqualTo(List.of());
        assertThat(shoppingList.get(customer3)).isEqualTo(List.of());
    }

    @Test
    @DisplayName("When customer has no money")
    public void test4() {
        var customer1 = Customer.builder().name("Name").surname("Surname").age(33).cash(new BigDecimal(0)).preferences("12").build();
        var customer2 = Customer.builder().name("Namea").surname("Surnamea").age(33).cash(new BigDecimal(0)).preferences("2").build();
        var customer3 = Customer.builder().name("Nameb").surname("Surnameb").age(33).cash(new BigDecimal(0)).preferences("3").build();
        var customers = List.of(customer1, customer2, customer3);
        when(customerRepository.getCustomer())
                .thenReturn(customers);
        when(productRepository.getProduct())
                .thenReturn(products);
        when(customerRepository.getCategoryMapper())
                .thenReturn(mappers);

        var shoppingList = shoppingService.init();

        assertThat(shoppingList.get(customer1)).isEqualTo(List.of());
        assertThat(shoppingList.get(customer2)).isEqualTo(List.of());
        assertThat(shoppingList.get(customer3)).isEqualTo(List.of());
    }

    @Test
    @DisplayName("When get customer list")
    public void test5() {
        when(customerRepository.getCustomer())
                .thenReturn(customers);
        when(productRepository.getProduct())
                .thenReturn(products);
        when(customerRepository.getCategoryMapper())
                .thenReturn(mappers);

        var customersDto = shoppingService.getAllCustomer();
        assertThat(customersDto).isEqualTo(List.of(customer1.toGetCustomerDto(), customer2.toGetCustomerDto(), customer3.toGetCustomerDto()));
    }

    @Test
    @DisplayName("When get product list")
    public void test6() {
        when(customerRepository.getCustomer())
                .thenReturn(customers);
        when(productRepository.getProduct())
                .thenReturn(products);
        when(customerRepository.getCategoryMapper())
                .thenReturn(mappers);

        shoppingService.init();
        var productsDto = shoppingService.getProductList();

        assertThat(productsDto).isEqualTo(List.of(productAGD.toGetProductDto(), productRTV.toGetProductDto(), productClothes.toGetProductDto()));
    }

    @Test
    @DisplayName("When get customer with max orders")
    public void test7() {
        when(customerRepository.getCustomer())
                .thenReturn(customers);
        when(productRepository.getProduct())
                .thenReturn(products);
        when(customerRepository.getCategoryMapper())
                .thenReturn(mappers);

        shoppingService.init();
        var clientWithMaxOrder = shoppingService.getClientWithMaxOrder();

        assertThat(clientWithMaxOrder).isEqualTo(customer1.toGetCustomerDto());
    }

    @Test
    @DisplayName("When get customer with max sum product cash")
    public void test8() {
        when(customerRepository.getCustomer())
                .thenReturn(customers);
        when(productRepository.getProduct())
                .thenReturn(products);
        when(customerRepository.getCategoryMapper())
                .thenReturn(mappers);

        shoppingService.init();
        var clientWithMaxSumProductPrice = shoppingService.getClientWithMaxSumProductPrice();

        assertThat(clientWithMaxSumProductPrice).isEqualTo(customer1.toGetCustomerDto());
    }

    @Test
    @DisplayName("When get products quantity information")
    public void test9() {
        ;
        when(customerRepository.getCustomer())
                .thenReturn(customers);
        when(productRepository.getProduct())
                .thenReturn(products);
        when(customerRepository.getCategoryMapper())
                .thenReturn(mappers);


        shoppingService.init();
        var productQuantityDto = ProductQuantityDto.builder().countProduct(Map.of("productAGD", 1L, "productRTV", 2L, "productClothes", 2L)).build();
        assertThat(shoppingService.productQuantity()).isEqualTo(productQuantityDto);
    }

    @Test
    @DisplayName("When get popular product order category")
    public void test10() {
        when(customerRepository.getCustomer())
                .thenReturn(customers);
        when(productRepository.getProduct())
                .thenReturn(products);
        when(customerRepository.getCategoryMapper())
                .thenReturn(mappers);

        shoppingService.init();
        var clientWithMaxSumProductPrice = shoppingService.checkCategoryPopularity();
        var productCategoryPopularityDto = ProductCategoryPopularityDto.builder().popularCategory(Map.of("AGD", 1L, "RTV", 2L, "Clothes", 2L)).build();

        assertThat(clientWithMaxSumProductPrice).isEqualTo(productCategoryPopularityDto);
    }
}
