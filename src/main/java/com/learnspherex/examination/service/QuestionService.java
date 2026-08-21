package com.learnspherex.examination.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.learnspherex.examination.dto.QuestionRequestDTO;
import com.learnspherex.examination.dto.QuestionResponseDTO;
import com.learnspherex.examination.entity.Question;
import com.learnspherex.examination.repository.QuestionRepository;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // POST - Add Question
    public QuestionResponseDTO addQuestion(QuestionRequestDTO dto) {

        Question question = new Question();

        question.setQuestionText(dto.getQuestionText());
        question.setOptionA(dto.getOptionA());
        question.setOptionB(dto.getOptionB());
        question.setOptionC(dto.getOptionC());
        question.setOptionD(dto.getOptionD());
        question.setCorrectAnswer(dto.getCorrectAnswer());

        Question savedQuestion = questionRepository.save(question);

        return convertToResponseDTO(savedQuestion);
    }

    // GET - Get All Questions
    public List<QuestionResponseDTO> getAllQuestions() {

        return questionRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    // GET - Get Question By ID
    public QuestionResponseDTO getQuestionById(Long id) {

        Question question = questionRepository.findById(id).orElse(null);

        if (question == null) {
            return null;
        }

        return convertToResponseDTO(question);
    }

    // PUT - Update Question
    public QuestionResponseDTO updateQuestion(Long id, QuestionRequestDTO dto) {

        Question existingQuestion =
                questionRepository.findById(id).orElse(null);

        if (existingQuestion != null) {

            existingQuestion.setQuestionText(dto.getQuestionText());
            existingQuestion.setOptionA(dto.getOptionA());
            existingQuestion.setOptionB(dto.getOptionB());
            existingQuestion.setOptionC(dto.getOptionC());
            existingQuestion.setOptionD(dto.getOptionD());
            existingQuestion.setCorrectAnswer(dto.getCorrectAnswer());

            Question updatedQuestion =
                    questionRepository.save(existingQuestion);

            return convertToResponseDTO(updatedQuestion);
        }

        return null;
    }

    // DELETE - Delete Question
    public void deleteQuestion(Long id) {

        questionRepository.deleteById(id);
    }

    // Entity → Response DTO
    private QuestionResponseDTO convertToResponseDTO(Question question) {

        QuestionResponseDTO dto = new QuestionResponseDTO();

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