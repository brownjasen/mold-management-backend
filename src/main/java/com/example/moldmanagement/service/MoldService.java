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
public class MoldService {

    private final MoldRepository moldRepository;
    private final ProcessRepository processRepository;

    @Autowired
    public MoldService(MoldRepository moldRepository, ProcessRepository processRepository) {
        this.moldRepository = moldRepository;
        this.processRepository = processRepository;
    }

    // 获取所有模具，按优先级排序
    public List<Mold> getAllMolds() {
        return moldRepository.findAllByOrderByPriorityAsc();
    }

    // 根据ID获取模具
    public Mold getMoldById(Long id) {
        return moldRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mold", "id", id));
    }

    // 创建新模具
    @Transactional
    public Mold createMold(Mold mold) {
        mold.setCompletionRate(0.0);
        mold.setStatus("notStarted");
        return moldRepository.save(mold);
    }

    // 更新模具
    @Transactional
    public Mold updateMold(Long id, Mold moldDetails) {
        Mold mold = getMoldById(id);
        
        mold.setMoldCode(moldDetails.getMoldCode());
        mold.setOrderTime(moldDetails.getOrderTime());
        mold.setPriority(moldDetails.getPriority());
        
        // 不直接更新以下字段，因为它们需要通过特定操作改变
        // mold.setStartTime(moldDetails.getStartTime());
        // mold.setEndTime(moldDetails.getEndTime());
        // mold.setCompletionRate(moldDetails.getCompletionRate());
        // mold.setStatus(moldDetails.getStatus());
        
        return moldRepository.save(mold);
    }

    // 开始设计（启动模具生产流程）
    @Transactional
    public Mold startDesign(Long id) {
        Mold mold = getMoldById(id);
        
        if (mold.getStartTime() == null) {
            mold.setStartTime(LocalDateTime.now());
            mold.setStatus("inProgress");
            return moldRepository.save(mold);
        }
        
        return mold; // 如果已经开始，则不做任何更改
    }

    // 更新模具完成度
    @Transactional
    public void updateCompletionRate(Long moldId) {
        Mold mold = getMoldById(moldId);
        List<Process> processes = mold.getProcesses();
        
        if (processes.isEmpty()) {
            return;
        }
        
        double totalWeight = 0;
        double completedWeight = 0;
        
        for (Process process : processes) {
            totalWeight += process.getWeight();
            
            if ("completed".equals(process.getStatus())) {
                completedWeight += process.getWeight();
            } else if ("inProgress".equals(process.getStatus())) {
                // 进行中的工序计算50%
                completedWeight += (process.getWeight() * 0.5);
            }
        }
        
        double completionRate = (completedWeight / totalWeight) * 100;
        mold.setCompletionRate(Math.round(completionRate * 10) / 10.0); // 四舍五入到一位小数
        
        // 更新状态
        if (completionRate >= 100) {
            mold.setStatus("completed");
            mold.setEndTime(LocalDateTime.now());
        } else if (completionRate > 0) {
            mold.setStatus("inProgress");
        }
        
        moldRepository.save(mold);
    }

    // 删除模具
    @Transactional
    public void deleteMold(Long id) {
        Mold mold = getMoldById(id);
        moldRepository.delete(mold);
    }
}
