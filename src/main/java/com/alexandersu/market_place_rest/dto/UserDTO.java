package com.alexandersu.market_place_rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Builder
@Data
public class UserDTO {

    private Long id;
    @NotEmpty
    private String username;
    private String name;
    private String phoneNumber;
    private String email;
    private List<ProductDTO> productsDTOList;
}
