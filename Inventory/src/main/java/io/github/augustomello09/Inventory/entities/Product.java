package io.github.augustomello09.Inventory.entities;

import io.github.augustomello09.Inventory.exceptions.BusinessException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_product")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "sku", nullable = false, unique = true)
    private String sku;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    private boolean active;

    protected Product(){}

    public Product(String name, BigDecimal basePrice, boolean active, String sku, String description) {
        if(name == null || name.isBlank() || sku == null || sku.isBlank()){
            throw new BusinessException("name and sku is required");
        }

        if (basePrice == null || basePrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Base price is required and cannot be negative");
        }

        if (description == null || description.isBlank()) {
            throw new BusinessException("Description is required");
        }

        this.name = name;
        this.sku = sku;
        this.active = active;
        this.description = description;
        this.basePrice = basePrice;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }


}
