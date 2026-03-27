package io.github.augustomello09.Inventory.entities;

import io.github.augustomello09.Inventory.enums.MovementSource;
import io.github.augustomello09.Inventory.enums.MovementType;
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
@Table(name = "tb_stock_movement")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false)
    private MovementType movementType;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_source", nullable = false)
    private MovementSource movementSource;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    protected StockMovement() {}

    public StockMovement(String description, int quantity, MovementSource movementSource, MovementType movementType, Inventory inventory) {
        if (quantity == 0){
            throw new BusinessException("Quantity cannot be 0");
        }

        if (movementSource == null || movementType == null) {
            throw new BusinessException("Movement Source/Type cannot be null");
        }

        if (inventory == null) {
            throw new BusinessException("Inventory cannot be null");
        }

        this.quantity = quantity;
        this.description = description;
        this.movementType = movementType;
        this.movementSource = movementSource;

        this.inventory = inventory;

        this.createdAt = LocalDate.now();
    }
}
