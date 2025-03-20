package com.example.moldmanagement.repository;

import com.example.moldmanagement.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {
    List<Process> findByMoldIdAndModuleType(Long moldId, String moduleType);
}
