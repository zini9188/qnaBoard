package com.qnaBoard.question.dto;

import com.qnaBoard.question.entity.Question;
import lombok.Getter;

@Getter
public class QuestionPostDto {
    private Long memberId;
    private String title;
    private String content;
    private Question.Access access;
}
