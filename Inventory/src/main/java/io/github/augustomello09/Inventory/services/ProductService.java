package io.github.augustomello09.Inventory.services;

import io.github.augustomello09.Inventory.dtos.ProductInsertDTORequest;
import io.github.augustomello09.Inventory.dtos.ProductDTOResponse;
import io.github.augustomello09.Inventory.dtos.ProductUpdateDTORequest;
import io.github.augustomello09.Inventory.entities.Product;
import io.github.augustomello09.Inventory.exceptions.BusinessException;
import io.github.augustomello09.Inventory.exceptions.ResourceNotFoundException;
import io.github.augustomello09.Inventory.mappers.ProductMapper;
import io.github.augustomello09.Inventory.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDTOResponse create(ProductInsertDTORequest request) {
        if (productRepository.existByName(request.getSku())) {
            throw new BusinessException("SKU aready exist.");
        }

        Product product = productMapper.toEntity(request);

        productRepository.save(product);

        return productMapper.toDto(product);

    }

    public ProductDTOResponse update(Long id, ProductUpdateDTORequest request) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ID Not Found.")
        );

        if (request.getName() != null) {
            product.setName(request.getName());
            product.setUpdatedAt(LocalDate.now());
        }

        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
            product.setUpdatedAt(LocalDate.now());
        }

        if (request.getBasePrice() != null) {
            product.setBasePrice(request.getBasePrice());
            product.setUpdatedAt(LocalDate.now());
        }

        if (request.getActive() != null) {
            product.setActive(request.getActive());
            product.setUpdatedAt(LocalDate.now());
        }

        return productMapper.toDto(product);
    }

    public void active(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ID Not Found.")
        );
        product.setActive(!product.isActive());
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public ProductDTOResponse findProduct(String name, String sku) {
        Product entity;
        if (name != null){
            entity = productRepository.findByName(name)
                    .orElseThrow(() -> new ResourceNotFoundException("Name Not Found"));
        } else if (sku != null) {
            entity = productRepository.findBySku(sku)
                    .orElseThrow(() -> new ResourceNotFoundException("SKU Not Found"));
        } else {
            throw new BusinessException("Please provide at least the Name or SKU for the search.");
        }

        return productMapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTOResponse> findAllPaged(Pageable page) {
        Page<Product> paged = productRepository.findAll(page);
        return paged.map(productMapper::toDto);
    }
}
