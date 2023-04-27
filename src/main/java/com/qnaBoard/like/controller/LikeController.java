package com.qnaBoard.like.controller;

import com.qnaBoard.like.dto.LikeDto;
import com.qnaBoard.like.entity.Like;
import com.qnaBoard.like.mapper.LikeMapper;
import com.qnaBoard.like.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.Positive;
import java.net.URI;

import static com.qnaBoard.utils.Constant.DEFAULT_LIKE_URI;

@RestController
@RequestMapping(DEFAULT_LIKE_URI)
public class LikeController {

    private final LikeService likeService;
    private final LikeMapper likeMapper;

    public LikeController(LikeService likeService, LikeMapper likeMapper) {
        this.likeService = likeService;
        this.likeMapper = likeMapper;
    }

    @PostMapping
    public ResponseEntity<Like> likeQuestion(@RequestBody LikeDto.Post post) {
        likeService.addLike(likeMapper.LikeDtoPostToLike(post));
        URI uri = UriComponentsBuilder.newInstance().build(DEFAULT_LIKE_URI);
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{like-id}")
    public void deleteLike(@PathVariable("like-id") @Positive long likeId,
                           @RequestParam @Positive long questionId) {
        likeService.delete(likeId, questionId);
    }
}
