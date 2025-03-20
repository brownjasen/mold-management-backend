package com.example.moldmanagement.repository;

import com.example.moldmanagement.entity.RepairRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairRecordRepository extends JpaRepository<RepairRecord, Long> {
    List<RepairRecord> findByMoldId(Long moldId);
    List<RepairRecord> findByStatus(String status);
}
