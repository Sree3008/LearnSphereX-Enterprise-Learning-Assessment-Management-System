package com.learnspherex.examination.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learnspherex.examination.dto.ExamAttemptRequestDTO;
import com.learnspherex.examination.dto.ExamAttemptResponseDTO;
import com.learnspherex.examination.entity.ExamAttempt;
import com.learnspherex.examination.entity.Examination;
import com.learnspherex.examination.entity.Question;
import com.learnspherex.examination.repository.ExamAttemptRepository;
import com.learnspherex.examination.repository.ExaminationRepository;

@Service
@Transactional
public class ExamAttemptService {

    private final ExamAttemptRepository examAttemptRepository;

    private final ExaminationRepository examinationRepository;

    public ExamAttemptService(
            ExamAttemptRepository examAttemptRepository,
            ExaminationRepository examinationRepository) {

        this.examAttemptRepository = examAttemptRepository;
        this.examinationRepository = examinationRepository;
    }

    public ExamAttemptResponseDTO submitExam(
            ExamAttemptRequestDTO request) {

        long previousAttempts =
                examAttemptRepository.countByStudentIdAndExaminationId(
                        request.getStudentId(),
                        request.getExaminationId());

        if (previousAttempts >= 2) {
            throw new RuntimeException(
                    "Maximum 2 attempts allowed for this examination");
        }

        Examination examination =
                examinationRepository.findById(
                        request.getExaminationId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Examination not found with id: "
                                + request.getExaminationId()));

        int score = calculateScore(
                examination,
                request);

        int attemptNumber = (int) previousAttempts + 1;

        String grade = calculateGrade(
                score,
                examination.getQuestions().size());

        ExamAttempt attempt = new ExamAttempt();

        attempt.setStudentId(request.getStudentId());

        attempt.setExaminationId(
                request.getExaminationId());

        attempt.setAttemptNumber(attemptNumber);

        attempt.setScore(score);

        attempt.setGrade(grade);

        ExamAttempt savedAttempt =
                examAttemptRepository.save(attempt);

        return convertToDTO(savedAttempt);
    }

    public List<ExamAttemptResponseDTO> getAttempts(
            Long studentId,
            Long examinationId) {

        return examAttemptRepository
                .findByStudentIdAndExaminationId(
                        studentId,
                        examinationId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    private int calculateScore(
            Examination examination,
            ExamAttemptRequestDTO request) {

        int score = 0;

        if (examination.getQuestions() == null) {
            return score;
        }

        for (Question question : examination.getQuestions()) {

            String selectedAnswer =
                    request.getAnswers()
                            .get(question.getId());

            if (selectedAnswer != null
                    && selectedAnswer.equalsIgnoreCase(
                            question.getCorrectAnswer())) {

                score++;
            }
        }

        return score;
    }

    private String calculateGrade(
            int score,
            int totalQuestions) {

        if (totalQuestions == 0) {
            return "N/A";
        }

        double percentage =
                ((double) score / totalQuestions) * 100;

        if (percentage >= 90) {
            return "A+";
        } else if (percentage >= 80) {
            return "A";
        } else if (percentage >= 70) {
            return "B";
        } else if (percentage >= 60) {
            return "C";
        } else if (percentage >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

    private ExamAttemptResponseDTO convertToDTO(
            ExamAttempt attempt) {

        ExamAttemptResponseDTO dto =
                new ExamAttemptResponseDTO();

        dto.setId(attempt.getId());

        dto.setStudentId(
                attempt.getStudentId());

        dto.setExaminationId(
                attempt.getExaminationId());

        dto.setAttemptNumber(
                attempt.getAttemptNumber());

        dto.setScore(
                attempt.getScore());

        dto.setGrade(
                attempt.getGrade());

        return dto;
    }
}