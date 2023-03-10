package com.qnaBoard.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    ANSWERED_QUESTION(406, "답변 완료된 질문입니다."),
    DELETED_QUESTION(406, "삭제된 질문입니다."),
    NOT_AUTHOR_QUESTION(406, "해당 글의 작성자가 아닙니다."),
    MEMBER_EXIST(405, "이미 존재하는 회원입니다."),
    ANSWER_EXIST(405, "이미 존재하는 답변입니다."),
    MEMBER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
    QUESTION_NOT_FOUND(404, "존재하지 않는 질문입니다."),
    ANSWER_NOT_FOUND(404, "존재하지 않는 답변입니다.");
    private final int status;
    private final String message;
}
