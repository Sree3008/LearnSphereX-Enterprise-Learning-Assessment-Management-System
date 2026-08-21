package com.learnspherex.certificate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnspherex.certificate.entity.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    Optional<Certificate> findByCertificateNumber(String certificateNumber);

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}