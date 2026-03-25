package io.github.augustomello09.Inventory.controllers;

import io.github.augustomello09.Inventory.dtos.ApiResponseDTO;
import io.github.augustomello09.Inventory.dtos.InventoryDTOResponse;
import io.github.augustomello09.Inventory.dtos.InventoryInsertDTORequest;
import io.github.augustomello09.Inventory.services.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<InventoryDTOResponse>> create(@Valid @RequestBody InventoryInsertDTORequest request){
        InventoryDTOResponse inventoryDTOResponse = service.create(request);
        ApiResponseDTO<InventoryDTOResponse> response = new ApiResponseDTO<>(true, "inventory create successfully", inventoryDTOResponse);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}").buildAndExpand(response.getData().getId()).toUri()
        ).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponseDTO<InventoryDTOResponse>> findByProductAndWarehouse(
            @RequestParam Long productId,
            @RequestParam Long warehouseId) {

        InventoryDTOResponse data = service.findInventory(productId, warehouseId);

        ApiResponseDTO<InventoryDTOResponse> response = new ApiResponseDTO<>(
                true,
                "Inventory retrieved successfully",
                data
        );

        return ResponseEntity.ok(response);
    }


}
