package io.github.augustomello09.Inventory.mappers;

import io.github.augustomello09.Inventory.dtos.WarehouseDTOResponse;
import io.github.augustomello09.Inventory.dtos.WarehouseInsertDTORequest;
import io.github.augustomello09.Inventory.entities.Warehouse;
import org.mapstruct.Mapper;

@Mapper
public interface WarehouseMapper {

    WarehouseDTOResponse toDto(Warehouse entity);

    Warehouse toEntity(WarehouseInsertDTORequest request);
}
