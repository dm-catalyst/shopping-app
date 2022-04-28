package pl.catalyst.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.catalyst.controller.dto.ResponseDataDto;
import pl.catalyst.model.customer.dto.GetCustomerDto;
import pl.catalyst.model.product.dto.GetProductDto;
import pl.catalyst.service.ShoppingService;
import pl.catalyst.service.dto.ProductCategoryPopularityDto;
import pl.catalyst.service.dto.ProductQuantityDto;

import java.util.List;
import java.util.Map;

import static pl.catalyst.controller.dto.ResponseDataDto.toResponse;

@RestController
@RequestMapping("/shopping")
@RequiredArgsConstructor
public class ShoppingController {

    private final ShoppingService shoppingService;

    @GetMapping("/customers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDataDto<List<GetCustomerDto>> getCustomer() {
        return toResponse(shoppingService.getAllCustomer());
    }

    @GetMapping("/products")
    public ResponseDataDto<List<GetProductDto>> getProduct() {
        return toResponse(shoppingService.getProductList());
    }

    @GetMapping("/shopping")
    public ResponseDataDto<Map<GetCustomerDto, List<GetProductDto>>> getShoppingList() {
        return toResponse(shoppingService.getShoppingList());
    }

    @GetMapping("/customer-with-max-order")
    public ResponseDataDto<GetCustomerDto> getCustomerWithMaxOrders() {
        return toResponse(shoppingService.getClientWithMaxOrder());
    }

    @GetMapping("/customer-with-max-sum-product-price")
    public ResponseDataDto<GetCustomerDto> getCustomerWithMaxSumProductPrice() {
        return toResponse(shoppingService.getClientWithMaxSumProductPrice());
    }

    @GetMapping("/product-quantity")
    public ResponseDataDto<ProductQuantityDto> productQuantity() {
        return toResponse(shoppingService.productQuantity());
    }

    @GetMapping("/product-category-popularity")
    public ResponseDataDto<ProductCategoryPopularityDto> checkCategoryPopularity() {
        return toResponse(shoppingService.checkCategoryPopularity());
    }
}
