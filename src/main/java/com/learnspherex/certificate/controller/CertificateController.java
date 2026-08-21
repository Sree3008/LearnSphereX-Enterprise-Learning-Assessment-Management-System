package com.learnspherex.certificate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.learnspherex.certificate.dto.CertificateRequestDTO;
import com.learnspherex.certificate.dto.CertificateResponseDTO;
import com.learnspherex.certificate.service.CertificateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping("/generate")
    public ResponseEntity<CertificateResponseDTO> generateCertificate(
            @Valid @RequestBody CertificateRequestDTO request) {

        CertificateResponseDTO response =
                certificateService.generateCertificate(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateResponseDTO> getCertificateById(
            @PathVariable Long id) {

        CertificateResponseDTO response =
                certificateService.getCertificateById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify/{certificateNumber}")
    public ResponseEntity<CertificateResponseDTO> verifyCertificate(
            @PathVariable String certificateNumber) {

        CertificateResponseDTO response =
                certificateService.getCertificateByNumber(certificateNumber);

        return ResponseEntity.ok(response);
    }
}