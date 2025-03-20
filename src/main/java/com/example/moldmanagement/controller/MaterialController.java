package com.example.moldmanagement.controller;

import com.example.moldmanagement.entity.Material;
import com.example.moldmanagement.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    // 获取所有辅料
    @GetMapping
    public ResponseEntity<List<Material>> getAllMaterials() {
        List<Material> materials = materialService.getAllMaterials();
        return new ResponseEntity<>(materials, HttpStatus.OK);
    }

    // 获取单个辅料
    @GetMapping("/{id}")
    public ResponseEntity<Material> getMaterialById(@PathVariable Long id) {
        Material material = materialService.getMaterialById(id);
        return new ResponseEntity<>(material, HttpStatus.OK);
    }

    // 获取库存不足的辅料
    @GetMapping("/low-stock")
    public ResponseEntity<List<Material>> getLowStockMaterials() {
        List<Material> materials = materialService.getLowStockMaterials();
        return new ResponseEntity<>(materials, HttpStatus.OK);
    }

    // 搜索辅料
    @GetMapping("/search")
    public ResponseEntity<List<Material>> searchMaterials(@RequestParam String query) {
        List<Material> materials = materialService.searchMaterials(query);
        return new ResponseEntity<>(materials, HttpStatus.OK);
    }

    // 创建辅料
    @PostMapping
    public ResponseEntity<Material> createMaterial(@RequestBody Material material) {
        Material createdMaterial = materialService.createMaterial(material);
        return new ResponseEntity<>(createdMaterial, HttpStatus.CREATED);
    }

    // 更新辅料
    @PutMapping("/{id}")
    public ResponseEntity<Material> updateMaterial(
            @PathVariable Long id, @RequestBody Material material) {
        Material updatedMaterial = materialService.updateMaterial(id, material);
        return new ResponseEntity<>(updatedMaterial, HttpStatus.OK);
    }

    // 入库操作
    @PostMapping("/{id}/stock-in")
    public ResponseEntity<Material> stockIn(
            @PathVariable Long id, @RequestParam Integer quantity) {
        Material material = materialService.stockIn(id, quantity);
        return new ResponseEntity<>(material, HttpStatus.OK);
    }

    // 出库操作
    @PostMapping("/{id}/stock-out")
    public ResponseEntity<Material> stockOut(
            @PathVariable Long id, @RequestParam Integer quantity) {
        Material material = materialService.stockOut(id, quantity);
        return new ResponseEntity<>(material, HttpStatus.OK);
    }

    // 删除辅料
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
