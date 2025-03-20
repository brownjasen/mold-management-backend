package com.example.moldmanagement.service;

import com.example.moldmanagement.entity.Mold;
import com.example.moldmanagement.entity.RepairRecord;
import com.example.moldmanagement.exception.ResourceNotFoundException;
import com.example.moldmanagement.repository.MoldRepository;
import com.example.moldmanagement.repository.RepairRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RepairRecordService {

    private final RepairRecordRepository repairRecordRepository;
    private final MoldRepository moldRepository;

    @Autowired
    public RepairRecordService(RepairRecordRepository repairRecordRepository, MoldRepository moldRepository) {
        this.repairRecordRepository = repairRecordRepository;
        this.moldRepository = moldRepository;
    }

    // 获取所有返修记录
    public List<RepairRecord> getAllRepairRecords() {
        return repairRecordRepository.findAll();
    }

    // 获取单个返修记录
    public RepairRecord getRepairRecordById(Long id) {
        return repairRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RepairRecord", "id", id));
    }

    // 获取模具的所有返修记录
    public List<RepairRecord> getRepairRecordsByMoldId(Long moldId) {
        return repairRecordRepository.findByMoldId(moldId);
    }

    // 获取特定状态的返修记录
    public List<RepairRecord> getRepairRecordsByStatus(String status) {
        return repairRecordRepository.findByStatus(status);
    }

    // 创建返修记录
    @Transactional
    public RepairRecord createRepairRecord(Long moldId, RepairRecord repairRecord) {
        Mold mold = moldRepository.findById(moldId)
                .orElseThrow(() -> new ResourceNotFoundException("Mold", "id", moldId));
        
        repairRecord.setMold(mold);
        repairRecord.setRepairDate(LocalDateTime.now());
        repairRecord.setStatus("waiting"); // 初始状态为等待中
        
        return repairRecordRepository.save(repairRecord);
    }

    // 开始返修
    @Transactional
    public RepairRecord startRepair(Long id) {
        RepairRecord repairRecord = getRepairRecordById(id);
        
        if ("waiting".equals(repairRecord.getStatus())) {
            repairRecord.setStatus("repairing");
            return repairRecordRepository.save(repairRecord);
        }
        
        return repairRecord;
    }

    // 完成返修
    @Transactional
    public RepairRecord completeRepair(Long id) {
        RepairRecord repairRecord = getRepairRecordById(id);
        
        if ("repairing".equals(repairRecord.getStatus())) {
            repairRecord.setStatus("completed");
            return repairRecordRepository.save(repairRecord);
        }
        
        return repairRecord;
    }

    // 更新返修记录
    @Transactional
    public RepairRecord updateRepairRecord(Long id, RepairRecord repairRecordDetails) {
        RepairRecord repairRecord = getRepairRecordById(id);
        
        // 只更新可编辑的字段
        repairRecord.setPartName(repairRecordDetails.getPartName());
        repairRecord.setReason(repairRecordDetails.getReason());
        repairRecord.setResponsible(repairRecordDetails.getResponsible());
        repairRecord.setRepairHours(repairRecordDetails.getRepairHours());
        repairRecord.setCost(repairRecordDetails.getCost());
        repairRecord.setPriority(repairRecordDetails.getPriority());
        repairRecord.setDescription(repairRecordDetails.getDescription());
        
        // 不直接更新状态，状态通过特定方法修改
        // repairRecord.setStatus(repairRecordDetails.getStatus());
        
        return repairRecordRepository.save(repairRecord);
    }

    // 删除返修记录
    @Transactional
    public void deleteRepairRecord(Long id) {
        RepairRecord repairRecord = getRepairRecordById(id);
        
        // 只允许删除等待中的返修记录
        if ("waiting".equals(repairRecord.getStatus())) {
            repairRecordRepository.delete(repairRecord);
        } else {
            throw new IllegalStateException("Cannot delete a repair record that is in progress or completed");
        }
    }
}
