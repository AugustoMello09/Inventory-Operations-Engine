package io.github.augustomello09.Inventory.entities;

import io.github.augustomello09.Inventory.exceptions.BusinessException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_inventory", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id", "warehouse_id"})
})
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "minimum_stock", nullable = false)
    private int minimumStock;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "update_at")
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    protected Inventory(){}

    public Inventory(int quantity, int minimumStock, Product product, Warehouse warehouse) {
        if (quantity < 0 || minimumStock < 0) {
            throw new BusinessException("Quantity and minimum stock must be positive values.");
        }

        this.quantity = quantity;
        this.minimumStock = minimumStock;

        this.product = product;
        this.warehouse = warehouse;

        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
}
