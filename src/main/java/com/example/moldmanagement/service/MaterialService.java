package com.example.moldmanagement.service;

import com.example.moldmanagement.entity.Material;
import com.example.moldmanagement.exception.ResourceNotFoundException;
import com.example.moldmanagement.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    // 获取所有辅料
    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    // 获取单个辅料
    public Material getMaterialById(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material", "id", id));
    }

    // 创建辅料
    @Transactional
    public Material createMaterial(Material material) {
        material.setLastUpdate(LocalDateTime.now());
        return materialRepository.save(material);
    }

    // 更新辅料
    @Transactional
    public Material updateMaterial(Long id, Material materialDetails) {
        Material material = getMaterialById(id);
        
        material.setName(materialDetails.getName());
        material.setSpecification(materialDetails.getSpecification());
        material.setStock(materialDetails.getStock());
        material.setSafeStock(materialDetails.getSafeStock());
        material.setUnit(materialDetails.getUnit());
        material.setSupplier(materialDetails.getSupplier());
        material.setLastUpdate(LocalDateTime.now());
        
        return materialRepository.save(material);
    }

    // 入库操作
    @Transactional
    public Material stockIn(Long id, Integer quantity) {
        Material material = getMaterialById(id);
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        
        material.setStock(material.getStock() + quantity);
        material.setLastUpdate(LocalDateTime.now());
        
        return materialRepository.save(material);
    }

    // 出库操作
    @Transactional
    public Material stockOut(Long id, Integer quantity) {
        Material material = getMaterialById(id);
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        
        if (material.getStock() < quantity) {
            throw new IllegalStateException("Insufficient stock");
        }
        
        material.setStock(material.getStock() - quantity);
        material.setLastUpdate(LocalDateTime.now());
        
        return materialRepository.save(material);
    }

    // 获取库存不足的辅料
    public List<Material> getLowStockMaterials() {
        return materialRepository.findByStockLessThanSafeStock();
    }

    // 搜索辅料
    public List<Material> searchMaterials(String query) {
        return materialRepository.findByNameContainingOrSpecificationContaining(query, query);
    }

    // 删除辅料
    @Transactional
    public void deleteMaterial(Long id) {
        Material material = getMaterialById(id);
        materialRepository.delete(material);
    }
}
