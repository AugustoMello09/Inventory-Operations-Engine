package io.github.augustomello09.Inventory.controllers;

import io.github.augustomello09.Inventory.dtos.*;
import io.github.augustomello09.Inventory.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ProductDTOResponse>> create(@Valid @RequestBody ProductInsertDTORequest request){
        ProductDTOResponse product = service.create(request);
        ApiResponseDTO<ProductDTOResponse> response = new ApiResponseDTO<>(true, "Product created successfully", product);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(response.getData().getId()).toUri()
        ).body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponseDTO<ProductDTOResponse>> update(@PathVariable Long id, @RequestBody ProductUpdateDTORequest request){
        ProductDTOResponse product = service.update(id, request);
        ApiResponseDTO<ProductDTOResponse> response = new ApiResponseDTO<>(true, "Product update successfully", product);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping(value = "/active/{id}")
    public ResponseEntity<Void> active(@PathVariable Long id) {
        service.active(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/search")
    public ResponseEntity<ApiResponseDTO<ProductDTOResponse>> search(@RequestParam(required = false) String name,
                                                                     @RequestParam(required = false) String sku) {
        ProductDTOResponse product = service.findProduct(name, sku);
        ApiResponseDTO<ProductDTOResponse> response = new ApiResponseDTO<>(true, "Product find successfully", product);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTOResponse>> paged(Pageable page){
        Page<ProductDTOResponse> responses = service.findAllPaged(page);
        return ResponseEntity.ok().body(responses);
    }

}
