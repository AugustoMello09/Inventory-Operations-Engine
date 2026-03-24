package io.github.augustomello09.Inventory.controllers;

import io.github.augustomello09.Inventory.dtos.ApiResponseDTO;
import io.github.augustomello09.Inventory.dtos.WarehouseDTOResponse;
import io.github.augustomello09.Inventory.dtos.WarehouseInsertDTORequest;
import io.github.augustomello09.Inventory.dtos.WarehouseUpdateDTORequest;
import io.github.augustomello09.Inventory.services.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService service;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<WarehouseDTOResponse>> create(@Valid @RequestBody WarehouseInsertDTORequest request) {
        WarehouseDTOResponse warehouseDTOResponse = service.create(request);
        ApiResponseDTO<WarehouseDTOResponse> response = new ApiResponseDTO<>(true, "Warehouse create successfully", warehouseDTOResponse);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(response.getData().getId()).toUri()
        ).body(response);
    }

    @PatchMapping(value = "/active/{id}")
    public ResponseEntity<Void> active(@PathVariable Long id) {
        service.active(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Page<WarehouseDTOResponse>> paged(Pageable pageable){
        Page<WarehouseDTOResponse> responsePage = service.paged(pageable);
        return ResponseEntity.ok().body(responsePage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        WarehouseDTOResponse response = service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponseDTO<WarehouseDTOResponse>> update(@PathVariable Long id,@RequestBody WarehouseUpdateDTORequest request){
        WarehouseDTOResponse warehouseDTOResponse = service.update(id, request);
        ApiResponseDTO<WarehouseDTOResponse> response = new ApiResponseDTO<>(true, "Warehouse update successfully", warehouseDTOResponse);
        return ResponseEntity.ok().body(response);
    }
}
