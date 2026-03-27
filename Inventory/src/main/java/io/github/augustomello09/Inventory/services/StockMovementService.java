package io.github.augustomello09.Inventory.services;

import io.github.augustomello09.Inventory.entities.Inventory;
import io.github.augustomello09.Inventory.entities.StockMovement;
import io.github.augustomello09.Inventory.enums.MovementSource;
import io.github.augustomello09.Inventory.enums.MovementType;
import io.github.augustomello09.Inventory.exceptions.BusinessException;
import io.github.augustomello09.Inventory.exceptions.ResourceNotFoundException;
import io.github.augustomello09.Inventory.repositories.InventoryRepository;
import io.github.augustomello09.Inventory.repositories.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final InventoryRepository inventoryRepository;

    public void createMovement(String description, int quantity, MovementSource movementSource, MovementType movementType, Long inventoryId){
        validate(movementSource, movementType, quantity);

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("ID NotFound"));


        StockMovement stockMovement = new StockMovement(description, quantity, movementSource, movementType, inventory);

        stockMovementRepository.save(stockMovement);
    }

    private void validate(MovementSource source, MovementType type, int quantity) {

        if (source == null || type == null) {
            throw new BusinessException("Movement Source/Type cannot be null");
        }

        if (source == MovementSource.ORDER && type != MovementType.OUT) {
            throw new BusinessException("ORDER movements must be OUT");
        }

        if (source == MovementSource.RETURN && type != MovementType.IN) {
            throw new BusinessException("RETURN movements must be IN");
        }

        if (source == MovementSource.RESTOCK && type != MovementType.IN) {
            throw new BusinessException("RESTOCK movements must be IN");
        }

        if(quantity == 0) {
            throw new BusinessException("Quantity cannot be 0");
        }

    }
}
