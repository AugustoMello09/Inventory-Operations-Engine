package io.github.augustomello09.Inventory.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTOResponse {

    private Long id;
    private Long productId;
    private Long warehouseId;
    private int quantity;
    private int minimumStock;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
