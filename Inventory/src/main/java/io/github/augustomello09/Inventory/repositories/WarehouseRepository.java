package io.github.augustomello09.Inventory.repositories;

import io.github.augustomello09.Inventory.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    @Query(value = """
    SELECT COUNT(w) > 0 FROM Warehouse w WHERE w.name = :name
    """)
    boolean existName(@Param("name") String name);

    boolean existsByIdAndActiveTrue(Long id);


}
