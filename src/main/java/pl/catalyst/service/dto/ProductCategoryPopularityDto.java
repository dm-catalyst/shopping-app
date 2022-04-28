package pl.catalyst.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ProductCategoryPopularityDto {
    Map<String, Long> popularCategory;
}
