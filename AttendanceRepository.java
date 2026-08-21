package com.learnspherex.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnspherex.batch.entity.Attendance;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    List<Attendance> findByBatchId(Long batchId);

    List<Attendance> findByStudentId(Long studentId);

    List<Attendance> findByBatchIdAndStudentId(
            Long batchId,
            Long studentId);
}