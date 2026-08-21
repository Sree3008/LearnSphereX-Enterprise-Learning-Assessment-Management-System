package com.learnspherex.examination.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learnspherex.examination.dto.ExaminationRequestDTO;
import com.learnspherex.examination.dto.ExaminationResponseDTO;
import com.learnspherex.examination.dto.QuestionResponseDTO;
import com.learnspherex.examination.service.ExaminationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/examinations")
public class ExaminationController {

    private final ExaminationService examinationService;

    public ExaminationController(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    // POST - Add Examination
    @PostMapping
    public ExaminationResponseDTO addExamination(
            @Valid @RequestBody ExaminationRequestDTO dto) {

        return examinationService.addExamination(dto);
    }

    // GET - Get All Examinations
    @GetMapping
    public List<ExaminationResponseDTO> getAllExaminations() {

        return examinationService.getAllExaminations();
    }

    // GET - Get Examination By ID
    @GetMapping("/{id}")
    public ExaminationResponseDTO getExaminationById(
            @PathVariable Long id) {

        return examinationService.getExaminationById(id);
    }

    // GET - Get Questions By Examination ID
    @GetMapping("/{id}/questions")
    public List<QuestionResponseDTO> getQuestionsByExaminationId(
            @PathVariable Long id) {

        return examinationService.getQuestionsByExaminationId(id);
    }

    // POST - Add Questions To Examination
    @PostMapping("/{id}/questions")
    public ExaminationResponseDTO addQuestionsToExamination(
            @PathVariable Long id,
            @RequestBody List<Long> questionIds) {

        return examinationService.addQuestionsToExamination(id, questionIds);
    }

    // PUT - Update Examination
    @PutMapping("/{id}")
    public ExaminationResponseDTO updateExamination(
            @PathVariable Long id,
            @Valid @RequestBody ExaminationRequestDTO dto) {

        return examinationService.updateExamination(id, dto);
    }

    // DELETE - Delete Examination
    @DeleteMapping("/{id}")
    public String deleteExamination(@PathVariable Long id) {

        examinationService.deleteExamination(id);

        return "Examination deleted successfully";
    }
}