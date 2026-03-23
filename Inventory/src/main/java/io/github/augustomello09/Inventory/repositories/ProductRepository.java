package io.github.augustomello09.Inventory.repositories;

import io.github.augustomello09.Inventory.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
        SELECT count(p) > 0 from Product p WHERE p.sku = :sku
        """)
    boolean existByName(@Param("sku")String sku);

    @Query(value = """
    SELECT p from Product p WHERE p.name = :name
    """)
    Optional<Product> findByName(@Param("name")String name);

    @Query(value = """
    SELECT p from Product p WHERE p.sku = :sku
    """)
    Optional<Product> findBySku(@Param("sku")String sku);
}
