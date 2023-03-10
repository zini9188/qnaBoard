package com.qnaBoard.question.dto;

import com.qnaBoard.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class QuestionDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        private Long memberId;
        private String title;
        private String content;
        private Question.Access access;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private Long questionId;
        private Long memberId;
        private String title;
        private String content;
        private Question.Access access;

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long questionId;
        private String title;
        private String content;
        private String username;
        private Integer views;
        private String questionStatus;
        private String access;
    }
}
