package com.alexandersu.market_place_rest.mappers;

import com.alexandersu.market_place_rest.dto.UserDTO;
import com.alexandersu.market_place_rest.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ProductMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "user.products",target = "productsDTOList")
    UserDTO UserToUserDTO(User user);

    User UserDTOtoUser(UserDTO userDTO);
}
