package io.github.augustomello09.Inventory.mappers;

import io.github.augustomello09.Inventory.dtos.ProductDTOResponse;
import io.github.augustomello09.Inventory.dtos.ProductInsertDTORequest;
import io.github.augustomello09.Inventory.entities.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductDTOResponse toDto(Product entity);

    Product toEntity(ProductInsertDTORequest dto);
}
