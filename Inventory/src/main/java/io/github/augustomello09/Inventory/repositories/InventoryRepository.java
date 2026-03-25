package io.github.augustomello09.Inventory.repositories;

import io.github.augustomello09.Inventory.entities.Inventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    boolean existsByProductIdAndWarehouseId(Long productId, Long warehouseId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Inventory i " +
            "JOIN FETCH i.product p " +
            "JOIN FETCH i.warehouse w " +
            "WHERE p.id = :productId " +
            "AND w.id = :warehouseId ")
    Optional<Inventory> findByProductAndWarehouse(
            @Param("productId") Long productId,
            @Param("warehouseId") Long warehouseId
    );
}
