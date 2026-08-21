package com.learnspherex.batch.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


import org.springframework.stereotype.Service;

import com.learnspherex.batch.entity.BatchEnrollment;
import com.learnspherex.batch.repository.BatchEnrollmentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BatchEnrollmentServiceImpl implements BatchEnrollmentService {

    private final BatchEnrollmentRepository enrollmentRepository;

    public BatchEnrollmentServiceImpl(BatchEnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public BatchEnrollment enrollStudent(BatchEnrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public List<BatchEnrollment> getEnrollmentsByBatchId(Long batchId) {
        return enrollmentRepository.findByBatchId(batchId);
    }

    @Override
    public List<BatchEnrollment> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    @Override
    public BatchEnrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Enrollment not found with id: " + id));
    }

    @Override
    public BatchEnrollment updateEnrollment(
            Long id, BatchEnrollment enrollment) {

        BatchEnrollment existing = getEnrollmentById(id);

        existing.setBatchId(enrollment.getBatchId());
        existing.setStudentId(enrollment.getStudentId());
        existing.setEnrollmentDate(enrollment.getEnrollmentDate());
        existing.setStatus(enrollment.getStatus());

        return enrollmentRepository.save(existing);
    }

    @Override
    public void deleteEnrollment(Long id) {
        BatchEnrollment existing = getEnrollmentById(id);
        enrollmentRepository.delete(existing);
    }
}