package pl.catalyst.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class ProductQuantityDto {
    public Map<String, Long> countProduct;
}
