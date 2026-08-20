package com.learnspherex.certificate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.learnspherex.certificate.dto.CertificateRequestDTO;
import com.learnspherex.certificate.dto.CertificateResponseDTO;
import com.learnspherex.certificate.service.CertificateService;

@Controller
public class CertificateViewController {

    private final CertificateService certificateService;

    public CertificateViewController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    // Display certificate generation page
    @GetMapping("/certificate")
    public String showCertificatePage(Model model) {

        model.addAttribute(
                "certificateRequest",
                new CertificateRequestDTO());

        return "certificate/certificate";
    }

    // Generate certificate and redirect to certificate page
    @PostMapping("/certificate/generate")
    public String generateCertificate(
            @ModelAttribute("certificateRequest")
            CertificateRequestDTO request) {

        CertificateResponseDTO certificate =
                certificateService.generateCertificate(request);

        return "redirect:/certificate/view/"
                + certificate.getId();
    }

    // Display generated certificate
    @GetMapping("/certificate/view/{id}")
    public String viewCertificate(
            @PathVariable Long id,
            Model model) {

        CertificateResponseDTO certificate =
                certificateService.getCertificateById(id);

        model.addAttribute("certificate", certificate);

        return "certificate/certificate-view";
    }
}