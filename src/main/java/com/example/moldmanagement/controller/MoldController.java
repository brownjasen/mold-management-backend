package com.example.moldmanagement.controller;

import com.example.moldmanagement.entity.Mold;
import com.example.moldmanagement.service.MoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/molds")
public class MoldController {

    private final MoldService moldService;

    @Autowired
    public MoldController(MoldService moldService) {
        this.moldService = moldService;
    }

    // 获取所有模具
    @GetMapping
    public ResponseEntity<List<Mold>> getAllMolds() {
        List<Mold> molds = moldService.getAllMolds();
        return new ResponseEntity<>(molds, HttpStatus.OK);
    }

    // 获取单个模具
    @GetMapping("/{id}")
    public ResponseEntity<Mold> getMoldById(@PathVariable Long id) {
        Mold mold = moldService.getMoldById(id);
        return new ResponseEntity<>(mold, HttpStatus.OK);
    }

    // 创建模具
    @PostMapping
    public ResponseEntity<Mold> createMold(@RequestBody Mold mold) {
        Mold createdMold = moldService.createMold(mold);
        return new ResponseEntity<>(createdMold, HttpStatus.CREATED);
    }

    // 更新模具
    @PutMapping("/{id}")
    public ResponseEntity<Mold> updateMold(@PathVariable Long id, @RequestBody Mold mold) {
        Mold updatedMold = moldService.updateMold(id, mold);
        return new ResponseEntity<>(updatedMold, HttpStatus.OK);
    }

    // 开始设计（启动生产流程）
    @PostMapping("/{id}/start-design")
    public ResponseEntity<Mold> startDesign(@PathVariable Long id) {
        Mold mold = moldService.startDesign(id);
        return new ResponseEntity<>(mold, HttpStatus.OK);
    }

    // 删除模具
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMold(@PathVariable Long id) {
        moldService.deleteMold(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
