package com.alexandersu.market_place_rest.mappers;

import com.alexandersu.market_place_rest.dto.ProductDTO;
import com.alexandersu.market_place_rest.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO ProductToProductDTO (Product product);
    Product DTOmapToProduct (ProductDTO productDTO);
}
