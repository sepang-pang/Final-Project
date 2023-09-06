package com.team6.finalproject.club.report.repository;

import com.team6.finalproject.club.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllById(Long id);
}
