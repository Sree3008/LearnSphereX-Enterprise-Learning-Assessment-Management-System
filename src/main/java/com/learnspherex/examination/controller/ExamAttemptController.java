package com.learnspherex.examination.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.learnspherex.examination.dto.ExamAttemptRequestDTO;
import com.learnspherex.examination.dto.ExamAttemptResponseDTO;
import com.learnspherex.examination.service.ExamAttemptService;

@RestController
@RequestMapping("/api/exam-attempts")
public class ExamAttemptController {

    private final ExamAttemptService examAttemptService;

    public ExamAttemptController(
            ExamAttemptService examAttemptService) {

        this.examAttemptService = examAttemptService;
    }

    @PostMapping("/submit")
    public ResponseEntity<ExamAttemptResponseDTO> submitExam(
            @RequestBody ExamAttemptRequestDTO request) {

        ExamAttemptResponseDTO response =
                examAttemptService.submitExam(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{studentId}/{examinationId}")
    public ResponseEntity<List<ExamAttemptResponseDTO>> getAttempts(
            @PathVariable Long studentId,
            @PathVariable Long examinationId) {

        return ResponseEntity.ok(
                examAttemptService.getAttempts(
                        studentId,
                        examinationId));
    }
}