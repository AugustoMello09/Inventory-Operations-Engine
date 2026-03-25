package io.github.augustomello09.Inventory.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryInsertDTORequest {

    @NotNull(message = "ProductId is required")
    private Long productId;

    @NotNull(message = "WarehousetId is required")
    private Long warehouseId;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "quantity cannot be negative")
    private int quantity;

    @NotNull(message = "minimumStock is required")
    @PositiveOrZero(message = "minimum Stock cannot be negative")
    private int minimumStock;
}
