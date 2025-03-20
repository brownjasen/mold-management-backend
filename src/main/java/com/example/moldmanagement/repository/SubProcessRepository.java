package com.example.moldmanagement.repository;

import com.example.moldmanagement.entity.SubProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubProcessRepository extends JpaRepository<SubProcess, Long> {
    List<SubProcess> findByProcessId(Long processId);
}
