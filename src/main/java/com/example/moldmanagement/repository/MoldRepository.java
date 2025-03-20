package com.example.moldmanagement.repository;

import com.example.moldmanagement.entity.Mold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoldRepository extends JpaRepository<Mold, Long> {
    List<Mold> findAllByOrderByPriorityAsc();
    Optional<Mold> findByMoldCode(String moldCode);
}
