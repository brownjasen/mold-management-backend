package com.example.moldmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RepairRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime repairDate;
    private String partName;
    private String reason;
    private String responsible;
    private Double repairHours;
    private Double cost;
    private String priority; // high, medium, low
    private String status; // waiting, repairing, completed
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "mold_id")
    @JsonIgnore
    private Mold mold;
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(LocalDateTime repairDate) {
        this.repairDate = repairDate;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public Double getRepairHours() {
        return repairHours;
    }

    public void setRepairHours(Double repairHours) {
        this.repairHours = repairHours;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Mold getMold() {
        return mold;
    }

    public void setMold(Mold mold) {
        this.mold = mold;
    }
}
