package com.learnspherex.examination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnspherex.examination.entity.ExamAttempt;

public interface ExamAttemptRepository
        extends JpaRepository<ExamAttempt, Long> {

    long countByStudentIdAndExaminationId(
            Long studentId,
            Long examinationId);

    List<ExamAttempt> findByStudentIdAndExaminationId(
            Long studentId,
            Long examinationId);
}