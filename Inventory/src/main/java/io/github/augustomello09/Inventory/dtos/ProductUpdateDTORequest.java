package io.github.augustomello09.Inventory.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDTORequest {

    private String name;
    private String description;
    private BigDecimal basePrice;
    private Boolean active;

}
