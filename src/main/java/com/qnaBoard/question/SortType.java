package com.qnaBoard.question;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum SortType {
    NEWEST("createAt", Sort.Direction.DESC),
    OLDEST("createAt", Sort.Direction.ASC),
    MOST_LIKE("likes", Sort.Direction.DESC),
    LESS_LIKE("likes", Sort.Direction.ASC),
    MOST_VIEW("view", Sort.Direction.DESC),
    LESS_VIEW("view", Sort.Direction.ASC);

    SortType(String column, Sort.Direction direction) {
        this.column = column;
        this.direction = direction;
    }

    private String column;
    private Sort.Direction direction;
}
