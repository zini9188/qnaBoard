package com.qnaBoard.answer.dto;

import com.qnaBoard.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class AnswerDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        private Long questionId;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private Long answerId;
        private Long questionId;
        private String content;
        public void setAnswerId(long answerId){
            this.answerId = answerId;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long answerId;
        private String content;
        private Question.Access access;
        private LocalDateTime createdAt;
        private Question question;
    }
}
