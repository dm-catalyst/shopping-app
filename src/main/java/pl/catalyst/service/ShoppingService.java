package pl.catalyst.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.impl.collector.Collectors2;
import org.springframework.stereotype.Service;
import pl.catalyst.config.validator.Validator;
import pl.catalyst.model.customer.Customer;
import pl.catalyst.model.customer.CustomerValidator;
import pl.catalyst.model.customer.mappers.CategoryValidator;
import pl.catalyst.model.product.Product;
import pl.catalyst.model.customer.dto.GetCustomerDto;
import pl.catalyst.model.product.ProductValidator;
import pl.catalyst.model.product.dto.GetProductDto;
import pl.catalyst.model.customer.repository.CustomerRepository;
import pl.catalyst.model.product.repository.ProductRepository;
import pl.catalyst.service.dto.ProductCategoryPopularityDto;
import pl.catalyst.service.dto.ProductQuantityDto;
import pl.catalyst.service.exception.ShoppingServiceException;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


import static pl.catalyst.model.customer.CustomerUtils.toPreference;
import static pl.catalyst.model.customer.mappers.CustomerCategoryViewUtils.toDescription;
import static pl.catalyst.model.product.ProductUtils.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShoppingService {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    private Map<Customer, List<Product>> shopping;


    @PostConstruct
    protected Map<Customer, List<Product>> init() {
        this.shopping = getShopping();
        return this.shopping;
    }

    /**
     * @return Customer with a list of purchased products
     */

    private Map<Customer, List<Product>> getShopping() {

        var customers = customerRepository.getCustomer()
                .stream()
                .peek(customer -> Validator.validate(new CustomerValidator(), customer))
                .toList();
        var products = productRepository.getProduct()
                .stream()
                .peek(product -> Validator.validate(new ProductValidator(), product))
                .toList();
        var mapper = customerRepository.getCategoryMapper()
                .stream()
                .peek(categoryView -> Validator.validate(new CategoryValidator(), categoryView))
                .toList();

        return customers.stream()
                .collect(Collectors.toMap(Function.identity(), toPreference))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entryMap -> Arrays.stream(entryMap.getValue().split(""))
                        .filter(symbol -> !mapper.isEmpty() && mapper.size() >= Integer.parseInt(symbol) && !symbol.equalsIgnoreCase("0"))
                        .map(symbol -> mapper.get(Integer.parseInt(symbol) - 1))
                        .map(toDescription)
                        .map(category -> products.stream().filter(product ->
                                product.checkCategory(category) && product.checkQuantity() && entryMap.getKey().checkHasMoney(toPrice.apply(product)))
                                .peek(Product::reduceQuantity)
                                .peek(product -> entryMap.getKey().reduceCash(toPrice.apply(product)))
                                .toList()
                        )
                        .flatMap(Collection::stream)
                        .toList()
                ));
    }

    /**
     * @return Customers list
     */

    public List<GetCustomerDto> getAllCustomer() {
        var customer = customerRepository.getCustomer()
                .stream()
                .map(Customer::toGetCustomerDto)
                .toList();
        log.info("Customer list: " + customer);
        return customer;
    }

    /**
     * @return Products list
     */

    public List<GetProductDto> getProductList() {
        var products = productRepository.getProduct()
                .stream()
                .map(Product::toGetProductDto)
                .toList();
        log.info("Product list: " + products);
        return products;
    }

    /**
     * @return Customer shopping list
     */

    public Map<GetCustomerDto, List<GetProductDto>> getShoppingList() {
        var shoppingList = shopping.entrySet()
                .stream()
                .collect(Collectors.toMap(key -> key.getKey().toGetCustomerDto(), value -> value.getValue().stream().map(Product::toGetProductDto).collect(Collectors.toList())));
        log.info("Shopping list: " + String.valueOf(shoppingList));
        return shoppingList;
    }

    /**
     * @return Customer who bought the most products
     */

    public GetCustomerDto getClientWithMaxOrder() {
        var customer = this.shopping
                .entrySet()
                .stream()
                .max(Comparator.comparingInt(v -> v.getValue().size()))
                .orElseThrow(() -> new ShoppingServiceException("Cannot get Customer with max orders"))
                .getKey()
                .toGetCustomerDto();
        log.info("Customer with max order: " + customer);
        return customer;
    }

    /**
     * @return Customer whose total purchases were the highest
     */

    public GetCustomerDto getClientWithMaxSumProductPrice() {
        var customer = this.shopping.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, product -> product.getValue()
                        .stream()
                        .map(toPrice)
                        .collect(Collectors2.summarizingBigDecimal(v -> v))
                        .getSum()
                ))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElseThrow(() -> new ShoppingServiceException("Cannot get Client with max sum product cash"))
                .getKey()
                .toGetCustomerDto();
        log.info("Customer with max sum product cash: " + customer);
        return customer;
    }

    /**
     * @return Number of products selected by customers
     */

    public ProductQuantityDto productQuantity() {
        var countProduct = this.shopping.entrySet()
                .stream()
                .flatMap(v -> v.getValue().stream())
                .collect(Collectors.groupingBy(toProductName, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
        log.info("Product quantity: " + countProduct);
        return ProductQuantityDto.builder().countProduct(countProduct).build();
    }

    /**
     * @return List of Categories sorted descending order by dial popularity
     */

    public ProductCategoryPopularityDto checkCategoryPopularity() {
        var popularCategory = this.shopping.entrySet()
                .stream()
                .flatMap(v -> v.getValue().stream())
                .collect(Collectors.groupingBy(toProductCategory, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
        log.info("Popularity product category: " + popularCategory);
        return ProductCategoryPopularityDto.builder().popularCategory(popularCategory).build();
    }

    @Override
    public String toString() {
        return shopping.entrySet()
                .stream()
                .map(v -> v.getKey() + " Shopping list: " + v.getValue())
                .collect(Collectors.joining("\n"));
    }
}