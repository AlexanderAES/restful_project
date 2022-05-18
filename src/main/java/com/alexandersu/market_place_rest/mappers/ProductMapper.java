package com.alexandersu.market_place_rest.mappers;

import com.alexandersu.market_place_rest.dto.ProductDTO;
import com.alexandersu.market_place_rest.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "user.name", target = "productOwnerName")
    @Mapping(source = "user.phoneNumber",target = "productOwnerPhoneNumber")
    ProductDTO ProductToProductDTO (Product product);

    Product ProductDTOtoProduct (ProductDTO productDTO);


}
