package com.alexandersu.market_place_rest.mappers;

import com.alexandersu.market_place_rest.dto.UserDTO;
import com.alexandersu.market_place_rest.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);
}
