package com.example.moldmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Mold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String moldCode;
    private LocalDateTime orderTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer priority;
    private Double completionRate;
    private String status; // notStarted, inProgress, completed
    
    @OneToMany(mappedBy = "mold", cascade = CascadeType.ALL)
    private List<Process> processes = new ArrayList<>();
    
    @OneToMany(mappedBy = "mold", cascade = CascadeType.ALL)
    private List<RepairRecord> repairRecords = new ArrayList<>();
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMoldCode() {
        return moldCode;
    }

    public void setMoldCode(String moldCode) {
        this.moldCode = moldCode;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public List<RepairRecord> getRepairRecords() {
        return repairRecords;
    }

    public void setRepairRecords(List<RepairRecord> repairRecords) {
        this.repairRecords = repairRecords;
    }
}
