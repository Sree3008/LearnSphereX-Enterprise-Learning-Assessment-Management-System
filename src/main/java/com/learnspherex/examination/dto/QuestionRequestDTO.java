package com.learnspherex.examination.dto;

import jakarta.validation.constraints.NotBlank;

//This represents the data that comes from the user when creating/updating a question.

public class QuestionRequestDTO {
	@NotBlank(message = "Question text is required")
    private String questionText;
	@NotBlank(message = "Option A is required")
    private String optionA;
	@NotBlank(message = "Option B is required")
    private String optionB;
	@NotBlank(message = "Option C is required")
    private String optionC;
	@NotBlank(message = "Option D is required")
    private String optionD;
	@NotBlank(message = "Correct answer is required")
    private String correctAnswer;

    public QuestionRequestDTO() {
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
