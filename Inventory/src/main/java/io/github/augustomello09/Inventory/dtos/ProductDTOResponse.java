package io.github.augustomello09.Inventory.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTOResponse {

    private Long id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private boolean active;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
