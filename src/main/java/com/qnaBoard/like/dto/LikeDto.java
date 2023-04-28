package com.qnaBoard.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class LikeDto {

    @Getter
    @AllArgsConstructor
    public static class Post{
        long memberId;
        long questionId;

        public void addQuestionId(Long questionId) {
            this.questionId = questionId;
        }
    }
}
