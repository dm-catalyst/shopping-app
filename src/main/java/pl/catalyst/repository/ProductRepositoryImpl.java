package pl.catalyst.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.catalyst.model.product.Product;
import pl.catalyst.model.product.repository.ProductRepository;
import pl.catalyst.repository.exception.ProductRepositoryException;
import pl.catalyst.model.product.converter.ProductJsonConverter;
import pl.catalyst.utils.properties.ConfigProperties;

import java.util.List;

@AllArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ConfigProperties config;

    @Override
    public List<Product> getProduct() {
        var file = config.getConfigValue("product-filename");
        return new ProductJsonConverter()
                .fromJson(file)
                .orElseThrow(() -> new ProductRepositoryException("Cannot get products from file"));
    }
}
