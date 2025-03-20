package com.example.moldmanagement.service;

import com.example.moldmanagement.entity.Process;
import com.example.moldmanagement.entity.SubProcess;
import com.example.moldmanagement.exception.ResourceNotFoundException;
import com.example.moldmanagement.repository.ProcessRepository;
import com.example.moldmanagement.repository.SubProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubProcessService {

    private final SubProcessRepository subProcessRepository;
    private final ProcessRepository processRepository;
    private final ProcessService processService;

    @Autowired
    public SubProcessService(SubProcessRepository subProcessRepository, 
                            ProcessRepository processRepository,
                            ProcessService processService) {
        this.subProcessRepository = subProcessRepository;
        this.processRepository = processRepository;
        this.processService = processService;
    }

    // 获取子工序
    public SubProcess getSubProcessById(Long id) {
        return subProcessRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubProcess", "id", id));
    }

    // 获取工序的所有子工序
    public List<SubProcess> getSubProcessesByProcessId(Long processId) {
        return subProcessRepository.findByProcessId(processId);
    }

    // 创建子工序
    @Transactional
    public SubProcess createSubProcess(Long processId, SubProcess subProcess) {
        Process process = processRepository.findById(processId)
                .orElseThrow(() -> new ResourceNotFoundException("Process", "id", processId));
        
        subProcess.setProcess(process);
        subProcess.setStatus("notStarted");
        subProcess.setStartTime(null);
        subProcess.setEndTime(null);
        
        SubProcess savedSubProcess = subProcessRepository.save(subProcess);
        return savedSubProcess;
    }

    // 开始子工序
    @Transactional
    public SubProcess startSubProcess(Long id) {
        SubProcess subProcess = getSubProcessById(id);
        
        if ("notStarted".equals(subProcess.getStatus())) {
            subProcess.setStatus("inProgress");
            subProcess.setStartTime(LocalDateTime.now());
            
            SubProcess updatedSubProcess = subProcessRepository.save(subProcess);
            
            // 确保父工序也是进行中状态
            Process process = subProcess.getProcess();
            if (!"inProgress".equals(process.getStatus()) && !"completed".equals(process.getStatus())) {
                processService.startProcess(process.getId());
            }
            
            return updatedSubProcess;
        }
        
        return subProcess;
    }

    // 完成子工序
    @Transactional
    public SubProcess completeSubProcess(Long id) {
        SubProcess subProcess = getSubProcessById(id);
        
        if ("inProgress".equals(subProcess.getStatus())) {
            subProcess.setStatus("completed");
            subProcess.setEndTime(LocalDateTime.now());
            
            SubProcess updatedSubProcess = subProcessRepository.save(subProcess);
            
            // 检查所有子工序是否都已完成，如果是则完成父工序
            Process process = subProcess.getProcess();
            List<SubProcess> allSubProcesses = subProcessRepository.findByProcessId(process.getId());
            
            boolean allCompleted = allSubProcesses.stream()
                                    .allMatch(sp -> "completed".equals(sp.getStatus()));
            
            if (allCompleted && !"completed".equals(process.getStatus())) {
                processService.completeProcess(process.getId());
            }
            
            return updatedSubProcess;
        }
        
        return subProcess;
    }

    // 删除子工序
    @Transactional
    public void deleteSubProcess(Long id) {
        SubProcess subProcess = getSubProcessById(id);
        
        // 只允许删除未开始的子工序
        if ("notStarted".equals(subProcess.getStatus())) {
            subProcessRepository.delete(subProcess);
        } else {
            throw new IllegalStateException("Cannot delete a sub-process that has already started");
        }
    }
}
