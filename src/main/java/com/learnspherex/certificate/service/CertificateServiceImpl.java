package com.learnspherex.certificate.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.learnspherex.certificate.dto.CertificateRequestDTO;
import com.learnspherex.certificate.dto.CertificateResponseDTO;
import com.learnspherex.certificate.entity.Certificate;
import com.learnspherex.certificate.exception.DuplicateCertificateException;
import com.learnspherex.certificate.exception.ResourceNotFoundException;
import com.learnspherex.certificate.repository.CertificateRepository;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;

    public CertificateServiceImpl(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Override
    public CertificateResponseDTO generateCertificate(
            CertificateRequestDTO request) {

        boolean alreadyExists =
                certificateRepository.existsByStudentIdAndCourseId(
                        request.getStudentId(),
                        request.getCourseId());

        if (alreadyExists) {
            throw new DuplicateCertificateException(
                    "Certificate already exists for this student and course");
        }

        Certificate certificate = new Certificate();

        certificate.setCertificateNumber(
                "CERT-" + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase());

        certificate.setStudentId(request.getStudentId());

        certificate.setCourseId(request.getCourseId());

        // Temporary value.
        // Later this can come from the Examination,
        // Attendance and Project modules.
        certificate.setGrade("A");

        certificate.setIssueDate(LocalDate.now());

        certificate.setStatus("GENERATED");

        Certificate savedCertificate =
                certificateRepository.save(certificate);

        return convertToResponseDTO(savedCertificate);
    }

    @Override
    public CertificateResponseDTO getCertificateById(Long id) {

        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Certificate not found with id: " + id));

        return convertToResponseDTO(certificate);
    }

    @Override
    public CertificateResponseDTO getCertificateByNumber(
            String certificateNumber) {

        Optional<Certificate> certificate =
                certificateRepository.findByCertificateNumber(
                        certificateNumber);

        return convertToResponseDTO(
                certificate.orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Certificate not found with number: "
                                        + certificateNumber)));
    }

    private CertificateResponseDTO convertToResponseDTO(
            Certificate certificate) {

        CertificateResponseDTO response =
                new CertificateResponseDTO();

        response.setId(certificate.getId());

        response.setCertificateNumber(
                certificate.getCertificateNumber());

        response.setStudentId(
                certificate.getStudentId());

        response.setCourseId(
                certificate.getCourseId());

        response.setGrade(
                certificate.getGrade());

        response.setIssueDate(
                certificate.getIssueDate());

        response.setStatus(
                certificate.getStatus());

        return response;
    }
}