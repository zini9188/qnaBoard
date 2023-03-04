package com.qnaBoard.question.dto;

import com.qnaBoard.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionResponseDto {
    private Long questionId;
    private String title;
    private String content;
    private String username;
    private Integer views;
    private Question.QuestionStatus questionStatus;
    private Question.Access access;
}
