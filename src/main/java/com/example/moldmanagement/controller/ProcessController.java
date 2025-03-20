package com.example.moldmanagement.controller;

import com.example.moldmanagement.entity.Process;
import com.example.moldmanagement.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/processes")
public class ProcessController {

    private final ProcessService processService;

    @Autowired
    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    // 获取单个工序
    @GetMapping("/{id}")
    public ResponseEntity<Process> getProcessById(@PathVariable Long id) {
        Process process = processService.getProcessById(id);
        return new ResponseEntity<>(process, HttpStatus.OK);
    }

    // 获取模具的特定类型工序
    @GetMapping("/mold/{moldId}/module/{moduleType}")
    public ResponseEntity<List<Process>> getProcessesByMoldAndType(
            @PathVariable Long moldId, @PathVariable String moduleType) {
        List<Process> processes = processService.getProcessesByMoldAndType(moldId, moduleType);
        return new ResponseEntity<>(processes, HttpStatus.OK);
    }

    // 创建工序
    @PostMapping("/mold/{moldId}")
    public ResponseEntity<Process> createProcess(
            @PathVariable Long moldId, @RequestBody Process process) {
        Process createdProcess = processService.createProcess(moldId, process);
        return new ResponseEntity<>(createdProcess, HttpStatus.CREATED);
    }

    // 开始工序
    @PostMapping("/{id}/start")
    public ResponseEntity<Process> startProcess(@PathVariable Long id) {
        Process process = processService.startProcess(id);
        return new ResponseEntity<>(process, HttpStatus.OK);
    }

    // 完成工序
    @PostMapping("/{id}/complete")
    public ResponseEntity<Process> completeProcess(@PathVariable Long id) {
        Process process = processService.completeProcess(id);
        return new ResponseEntity<>(process, HttpStatus.OK);
    }

    // 更新工序
    @PutMapping("/{id}")
    public ResponseEntity<Process> updateProcess(
            @PathVariable Long id, @RequestBody Process process) {
        Process updatedProcess = processService.updateProcess(id, process);
        return new ResponseEntity<>(updatedProcess, HttpStatus.OK);
    }

    // 删除工序
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcess(@PathVariable Long id) {
        processService.deleteProcess(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
