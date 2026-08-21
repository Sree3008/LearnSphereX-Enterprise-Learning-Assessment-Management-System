package com.learnspherex.examination.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learnspherex.examination.service.ExaminationService;

@Controller
public class ExaminationViewController {

    private final ExaminationService examinationService;

    public ExaminationViewController(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    @GetMapping("/examination-page")
    public String examinationPage(Model model) {

        model.addAttribute(
                "examinations",
                examinationService.getAllExaminations()
        );

        return "examination/examinations";
    }

    @GetMapping("/examination-page/{id}")
    public String examinationDetails(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "examination",
                examinationService.getExaminationById(id)
        );

        model.addAttribute(
                "questions",
                examinationService.getQuestionsByExaminationId(id)
        );

        return "examination/examination-details";
    }

    @PostMapping("/examination-page/{id}/submit")
    public String submitExamination(
            @PathVariable Long id,
            @RequestParam Map<String, String> answers,
            Model model) {

        int score = 0;

        var questions =
                examinationService.getQuestionsByExaminationId(id);

        for (var question : questions) {

            String selectedAnswer =
                    answers.get("question_" + question.getId());

            if (selectedAnswer != null &&
                    selectedAnswer.equalsIgnoreCase(
                            question.getCorrectAnswer())) {

                score++;
            }
        }

        model.addAttribute("score", score);
        model.addAttribute("totalQuestions", questions.size());

        return "examination/result";
    }
}