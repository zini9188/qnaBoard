package com.qnaBoard.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class UriCreator {
    public static URI createUri(Long id){
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .build(id);
    }
}
