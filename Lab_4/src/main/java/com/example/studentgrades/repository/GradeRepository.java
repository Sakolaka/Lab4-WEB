package com.example.studentgrades.repository;

import com.example.studentgrades.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {}
