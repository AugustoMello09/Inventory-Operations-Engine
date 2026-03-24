package io.github.augustomello09.Inventory.entities;

import io.github.augustomello09.Inventory.exceptions.BusinessException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tb_warehouse")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    private boolean active;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    protected Warehouse(){}

    public Warehouse(String name, String location, boolean active) {
        if(name == null || name.isBlank() || location == null || location.isBlank()){
            throw new BusinessException("Name and Location is required");
        }

        this.name = name;
        this.location = location;
        this.active = active;

        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
}
