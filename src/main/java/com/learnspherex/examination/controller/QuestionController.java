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

import com.learnspherex.examination.dto.QuestionRequestDTO;
import com.learnspherex.examination.dto.QuestionResponseDTO;
import com.learnspherex.examination.service.QuestionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // POST - Add Question
    @PostMapping
    public QuestionResponseDTO addQuestion(
            @Valid @RequestBody QuestionRequestDTO dto) {

        return questionService.addQuestion(dto);
    }

    // GET - Get All Questions
    @GetMapping
    public List<QuestionResponseDTO> getAllQuestions() {

        return questionService.getAllQuestions();
    }

    // GET - Get Question By ID
    @GetMapping("/{id}")
    public QuestionResponseDTO getQuestionById(
            @PathVariable Long id) {

        return questionService.getQuestionById(id);
    }

    // PUT - Update Question
    @PutMapping("/{id}")
    public QuestionResponseDTO updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody QuestionRequestDTO dto) {

        return questionService.updateQuestion(id, dto);
    }

    // DELETE - Delete Question
    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id) {

        questionService.deleteQuestion(id);

        return "Question deleted successfully";
    }
}