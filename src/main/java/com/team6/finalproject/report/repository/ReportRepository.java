package com.team6.finalproject.report.repository;

import com.team6.finalproject.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long>, ReportRepositoryCustom{
    List<Report> findAllById(Long id);

}
