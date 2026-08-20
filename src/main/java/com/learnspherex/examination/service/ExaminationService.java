package com.learnspherex.examination.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learnspherex.examination.dto.ExaminationRequestDTO;
import com.learnspherex.examination.dto.ExaminationResponseDTO;
import com.learnspherex.examination.dto.QuestionResponseDTO;
import com.learnspherex.examination.entity.Examination;
import com.learnspherex.examination.entity.Question;
import com.learnspherex.examination.repository.ExaminationRepository;
import com.learnspherex.examination.repository.QuestionRepository;

@Service
@Transactional
public class ExaminationService {

    private final ExaminationRepository examinationRepository;
    private final QuestionRepository questionRepository;

    public ExaminationService(
            ExaminationRepository examinationRepository,
            QuestionRepository questionRepository) {

        this.examinationRepository = examinationRepository;
        this.questionRepository = questionRepository;
    }

    // POST - Add Examination
    public ExaminationResponseDTO addExamination(
            ExaminationRequestDTO dto) {

        Examination examination = new Examination();

        examination.setTitle(dto.getTitle());
        examination.setDescription(dto.getDescription());
        examination.setDurationMinutes(dto.getDurationMinutes());

        Examination savedExamination =
                examinationRepository.save(examination);

        return convertToDTO(savedExamination);
    }

    // GET - Get All Examinations
    public List<ExaminationResponseDTO> getAllExaminations() {

        return examinationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // GET - Get Examination By ID
    public ExaminationResponseDTO getExaminationById(Long id) {

        Examination examination =
                examinationRepository.findById(id).orElse(null);

        if (examination == null) {
            return null;
        }

        return convertToDTO(examination);
    }

    // GET - Get Questions By Examination ID
    public List<QuestionResponseDTO> getQuestionsByExaminationId(
            Long examinationId) {

        Examination examination =
                examinationRepository.findById(examinationId).orElse(null);

        if (examination == null) {
            return List.of();
        }

        if (examination.getQuestions() == null) {
            return List.of();
        }

        return examination.getQuestions()
                .stream()
                .map(this::convertQuestionToDTO)
                .toList();
    }

    // PUT - Update Examination
    public ExaminationResponseDTO updateExamination(
            Long id,
            ExaminationRequestDTO dto) {

        Examination examination =
                examinationRepository.findById(id).orElse(null);

        if (examination == null) {
            return null;
        }

        examination.setTitle(dto.getTitle());
        examination.setDescription(dto.getDescription());
        examination.setDurationMinutes(dto.getDurationMinutes());

        Examination updatedExamination =
                examinationRepository.save(examination);

        return convertToDTO(updatedExamination);
    }

    // DELETE - Delete Examination
    public void deleteExamination(Long id) {

        examinationRepository.deleteById(id);
    }

    // POST - Add Questions To Examination
    public ExaminationResponseDTO addQuestionsToExamination(
            Long examinationId,
            List<Long> questionIds) {

        Examination examination =
                examinationRepository
                        .findById(examinationId)
                        .orElse(null);

        if (examination == null) {
            return null;
        }

        List<Question> questions =
                questionRepository.findAllById(questionIds);

        examination.setQuestions(questions);

        Examination updatedExamination =
                examinationRepository.save(examination);

        return convertToDTO(updatedExamination);
    }

    // Convert Examination Entity To Response DTO
    private ExaminationResponseDTO convertToDTO(
            Examination examination) {

        ExaminationResponseDTO dto =
                new ExaminationResponseDTO();

        dto.setId(examination.getId());
        dto.setTitle(examination.getTitle());
        dto.setDescription(examination.getDescription());
        dto.setDurationMinutes(
                examination.getDurationMinutes()
        );

        // Always return an empty list instead of null
        if (examination.getQuestions() != null) {

            List<QuestionResponseDTO> questionDTOs =
                    examination.getQuestions()
                            .stream()
                            .map(this::convertQuestionToDTO)
                            .toList();

            dto.setQuestions(questionDTOs);

        } else {

            dto.setQuestions(List.of());
        }

        return dto;
    }

    // Convert Question Entity To Question Response DTO
    private QuestionResponseDTO convertQuestionToDTO(
            Question question) {

        QuestionResponseDTO dto =
                new QuestionResponseDTO();

        dto.setId(question.getId());
        dto.setQuestionText(question.getQuestionText());
        dto.setOptionA(question.getOptionA());
        dto.setOptionB(question.getOptionB());
        dto.setOptionC(question.getOptionC());
        dto.setOptionD(question.getOptionD());
        dto.setCorrectAnswer(question.getCorrectAnswer());

        return dto;
    }
}