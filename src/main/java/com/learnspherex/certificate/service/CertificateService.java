package com.learnspherex.certificate.service;

import com.learnspherex.certificate.dto.CertificateRequestDTO;
import com.learnspherex.certificate.dto.CertificateResponseDTO;

public interface CertificateService {

    CertificateResponseDTO generateCertificate(CertificateRequestDTO request);

    CertificateResponseDTO getCertificateById(Long id);

    CertificateResponseDTO getCertificateByNumber(String certificateNumber);
}