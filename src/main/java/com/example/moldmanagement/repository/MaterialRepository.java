package com.example.moldmanagement.repository;

import com.example.moldmanagement.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    // 添加@Query注解来明确指定查询
    @Query("SELECT m FROM Material m WHERE m.stock < m.safeStock")
    List<Material> findByStockLessThanSafeStock();
    
    List<Material> findByNameContainingOrSpecificationContaining(String name, String specification);
}
