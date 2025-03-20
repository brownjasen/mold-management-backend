package com.example.moldmanagement.controller;

import com.example.moldmanagement.entity.RepairRecord;
import com.example.moldmanagement.service.RepairRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repair-records")
public class RepairRecordController {

    private final RepairRecordService repairRecordService;

    @Autowired
    public RepairRecordController(RepairRecordService repairRecordService) {
        this.repairRecordService = repairRecordService;
    }

    // 获取所有返修记录
    @GetMapping
    public ResponseEntity<List<RepairRecord>> getAllRepairRecords() {
        List<RepairRecord> repairRecords = repairRecordService.getAllRepairRecords();
        return new ResponseEntity<>(repairRecords, HttpStatus.OK);
    }

    // 获取单个返修记录
    @GetMapping("/{id}")
    public ResponseEntity<RepairRecord> getRepairRecordById(@PathVariable Long id) {
        RepairRecord repairRecord = repairRecordService.getRepairRecordById(id);
        return new ResponseEntity<>(repairRecord, HttpStatus.OK);
    }

    // 获取模具的所有返修记录
    @GetMapping("/mold/{moldId}")
    public ResponseEntity<List<RepairRecord>> getRepairRecordsByMoldId(@PathVariable Long moldId) {
        List<RepairRecord> repairRecords = repairRecordService.getRepairRecordsByMoldId(moldId);
        return new ResponseEntity<>(repairRecords, HttpStatus.OK);
    }

    // 获取特定状态的返修记录
    @GetMapping("/status/{status}")
    public ResponseEntity<List<RepairRecord>> getRepairRecordsByStatus(@PathVariable String status) {
        List<RepairRecord> repairRecords = repairRecordService.getRepairRecordsByStatus(status);
        return new ResponseEntity<>(repairRecords, HttpStatus.OK);
    }

    // 创建返修记录
    @PostMapping("/mold/{moldId}")
    public ResponseEntity<RepairRecord> createRepairRecord(
            @PathVariable Long moldId, @RequestBody RepairRecord repairRecord) {
        RepairRecord createdRepairRecord = repairRecordService.createRepairRecord(moldId, repairRecord);
        return new ResponseEntity<>(createdRepairRecord, HttpStatus.CREATED);
    }

    // 开始返修
    @PostMapping("/{id}/start")
    public ResponseEntity<RepairRecord> startRepair(@PathVariable Long id) {
        RepairRecord repairRecord = repairRecordService.startRepair(id);
        return new ResponseEntity<>(repairRecord, HttpStatus.OK);
    }

    // 完成返修
    @PostMapping("/{id}/complete")
    public ResponseEntity<RepairRecord> completeRepair(@PathVariable Long id) {
        RepairRecord repairRecord = repairRecordService.completeRepair(id);
        return new ResponseEntity<>(repairRecord, HttpStatus.OK);
    }

    // 更新返修记录
    @PutMapping("/{id}")
    public ResponseEntity<RepairRecord> updateRepairRecord(
            @PathVariable Long id, @RequestBody RepairRecord repairRecord) {
        RepairRecord updatedRepairRecord = repairRecordService.updateRepairRecord(id, repairRecord);
        return new ResponseEntity<>(updatedRepairRecord, HttpStatus.OK);
    }

    // 删除返修记录
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepairRecord(@PathVariable Long id) {
        repairRecordService.deleteRepairRecord(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
