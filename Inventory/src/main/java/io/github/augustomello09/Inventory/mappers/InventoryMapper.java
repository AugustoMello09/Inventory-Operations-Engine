package io.github.augustomello09.Inventory.mappers;

import io.github.augustomello09.Inventory.dtos.InventoryDTOResponse;
import io.github.augustomello09.Inventory.dtos.InventoryInsertDTORequest;
import io.github.augustomello09.Inventory.entities.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface InventoryMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "warehouse.id", target = "warehouseId")
    InventoryDTOResponse toDto(Inventory entity);

    Inventory toEntity(InventoryInsertDTORequest request);
}
