package com.qnaBoard.question.dto;

import com.qnaBoard.question.entity.Question;
import lombok.Getter;

@Getter
public class QuestionPatchDto {
    private Long questionId;
    private Long memberId;
    private String title;
    private String content;
    private Question.Access access;

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
