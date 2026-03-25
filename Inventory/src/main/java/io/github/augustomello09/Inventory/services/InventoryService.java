package io.github.augustomello09.Inventory.services;

import io.github.augustomello09.Inventory.dtos.InventoryDTOResponse;
import io.github.augustomello09.Inventory.dtos.InventoryInsertDTORequest;
import io.github.augustomello09.Inventory.entities.Inventory;
import io.github.augustomello09.Inventory.exceptions.BusinessException;
import io.github.augustomello09.Inventory.exceptions.ResourceNotFoundException;
import io.github.augustomello09.Inventory.mappers.InventoryMapper;
import io.github.augustomello09.Inventory.repositories.InventoryRepository;
import io.github.augustomello09.Inventory.repositories.ProductRepository;
import io.github.augustomello09.Inventory.repositories.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    @Transactional
    public InventoryDTOResponse create(InventoryInsertDTORequest request) {
        if (!productRepository.existsByIdAndActiveTrue(request.getProductId())) {
            throw new BusinessException("Product is inactive or does not exist.");
        }

        if (!warehouseRepository.existsByIdAndActiveTrue(request.getWarehouseId())) {
            throw new BusinessException("Warehouse is inactive or does not exist.");
        }

        if (inventoryRepository.existsByProductIdAndWarehouseId(request.getProductId(), request.getWarehouseId())) {
            throw new BusinessException("This product already has a stock record in this warehouse.");
        }

        if (request.getQuantity() < 0 || request.getMinimumStock() < 0){
            throw new BusinessException("must be zero or positive values.");
        }

        Inventory inventory = inventoryMapper.toEntity(request);

        inventory.setProduct(productRepository.getReferenceById(request.getProductId()));
        inventory.setWarehouse(warehouseRepository.getReferenceById(request.getWarehouseId()));

        inventoryRepository.save(inventory);

        return inventoryMapper.toDto(inventory);
    }

    @Transactional(readOnly = true)
    public InventoryDTOResponse findInventory(Long productId, Long warehouseId) {
        Inventory inventory = inventoryRepository
                .findByProductAndWarehouse(productId, warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for this Product and Warehouse"));

        return inventoryMapper.toDto(inventory);
    }


}
