package com.example.moldmanagement.service;

import com.example.moldmanagement.entity.Mold;
import com.example.moldmanagement.entity.Process;
import com.example.moldmanagement.exception.ResourceNotFoundException;
import com.example.moldmanagement.repository.MoldRepository;
import com.example.moldmanagement.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProcessService {

    private final ProcessRepository processRepository;
    private final MoldRepository moldRepository;
    private final MoldService moldService;

    @Autowired
    public ProcessService(ProcessRepository processRepository, MoldRepository moldRepository, MoldService moldService) {
        this.processRepository = processRepository;
        this.moldRepository = moldRepository;
        this.moldService = moldService;
    }

    // 获取工序
    public Process getProcessById(Long id) {
        return processRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Process", "id", id));
    }

    // 获取模具的特定类型工序
    public List<Process> getProcessesByMoldAndType(Long moldId, String moduleType) {
        return processRepository.findByMoldIdAndModuleType(moldId, moduleType);
    }

    // 创建工序
    @Transactional
    public Process createProcess(Long moldId, Process process) {
        Mold mold = moldRepository.findById(moldId)
                .orElseThrow(() -> new ResourceNotFoundException("Mold", "id", moldId));
        
        process.setMold(mold);
        process.setStatus("notStarted");
        process.setStartTime(null);
        process.setEndTime(null);
        
        Process savedProcess = processRepository.save(process);
        moldService.updateCompletionRate(moldId);
        
        return savedProcess;
    }

    // 开始工序
    @Transactional
    public Process startProcess(Long id) {
        Process process = getProcessById(id);
        
        if ("notStarted".equals(process.getStatus())) {
            process.setStatus("inProgress");
            process.setStartTime(LocalDateTime.now());
            Process updatedProcess = processRepository.save(process);
            
            // 更新模具完成度
            moldService.updateCompletionRate(process.getMold().getId());
            
            return updatedProcess;
        }
        
        return process;
    }

    // 完成工序
    @Transactional
    public Process completeProcess(Long id) {
        Process process = getProcessById(id);
        
        if ("inProgress".equals(process.getStatus())) {
            process.setStatus("completed");
            process.setEndTime(LocalDateTime.now());
            Process updatedProcess = processRepository.save(process);
            
            // 更新模具完成度
            moldService.updateCompletionRate(process.getMold().getId());
            
            return updatedProcess;
        }
        
        return process;
    }

    // 更新工序
    @Transactional
    public Process updateProcess(Long id, Process processDetails) {
        Process process = getProcessById(id);
        
        process.setName(processDetails.getName());
        process.setWeight(processDetails.getWeight());
        process.setModuleType(processDetails.getModuleType());
        
        Process updatedProcess = processRepository.save(process);
        moldService.updateCompletionRate(process.getMold().getId());
        
        return updatedProcess;
    }

    // 删除工序
    @Transactional
    public void deleteProcess(Long id) {
        Process process = getProcessById(id);
        Long moldId = process.getMold().getId();
        
        processRepository.delete(process);
        moldService.updateCompletionRate(moldId);
    }
}
