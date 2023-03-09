package com.qnaBoard.dto;

import lombok.Getter;

@Getter
public class SingleResponseDto <T>{
    T data;

    public SingleResponseDto(T data) {
        this.data = data;
    }
}
