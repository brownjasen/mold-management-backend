package com.example.moldmanagement.controller;

import com.example.moldmanagement.entity.SubProcess;
import com.example.moldmanagement.service.SubProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sub-processes")
public class SubProcessController {

    private final SubProcessService subProcessService;

    @Autowired
    public SubProcessController(SubProcessService subProcessService) {
        this.subProcessService = subProcessService;
    }

    // 获取单个子工序
    @GetMapping("/{id}")
    public ResponseEntity<SubProcess> getSubProcessById(@PathVariable Long id) {
        SubProcess subProcess = subProcessService.getSubProcessById(id);
        return new ResponseEntity<>(subProcess, HttpStatus.OK);
    }

    // 获取工序的所有子工序
    @GetMapping("/process/{processId}")
    public ResponseEntity<List<SubProcess>> getSubProcessesByProcessId(@PathVariable Long processId) {
        List<SubProcess> subProcesses = subProcessService.getSubProcessesByProcessId(processId);
        return new ResponseEntity<>(subProcesses, HttpStatus.OK);
    }

    // 创建子工序
    @PostMapping("/process/{processId}")
    public ResponseEntity<SubProcess> createSubProcess(
            @PathVariable Long processId, @RequestBody SubProcess subProcess) {
        SubProcess createdSubProcess = subProcessService.createSubProcess(processId, subProcess);
        return new ResponseEntity<>(createdSubProcess, HttpStatus.CREATED);
    }

    // 开始子工序
    @PostMapping("/{id}/start")
    public ResponseEntity<SubProcess> startSubProcess(@PathVariable Long id) {
        SubProcess subProcess = subProcessService.startSubProcess(id);
        return new ResponseEntity<>(subProcess, HttpStatus.OK);
    }

    // 完成子工序
    @PostMapping("/{id}/complete")
    public ResponseEntity<SubProcess> completeSubProcess(@PathVariable Long id) {
        SubProcess subProcess = subProcessService.completeSubProcess(id);
        return new ResponseEntity<>(subProcess, HttpStatus.OK);
    }

    // 删除子工序
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubProcess(@PathVariable Long id) {
        subProcessService.deleteSubProcess(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
