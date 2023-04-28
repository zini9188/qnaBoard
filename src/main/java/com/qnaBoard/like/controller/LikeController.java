package com.qnaBoard.like.controller;

import com.qnaBoard.like.dto.LikeDto;
import com.qnaBoard.like.entity.Like;
import com.qnaBoard.like.mapper.LikeMapper;
import com.qnaBoard.like.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.net.URI;


@RestController
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;
    private final LikeMapper likeMapper;

    public LikeController(LikeService likeService, LikeMapper likeMapper) {
        this.likeService = likeService;
        this.likeMapper = likeMapper;
    }

    @PostMapping("/questions/{question-id}/likes")
    public ResponseEntity<Like> likeQuestion(@PathVariable("question-id") Long questionId,
                                             @RequestBody LikeDto.Post post) {
        post.addQuestionId(questionId);
        likeService.addLike(likeMapper.LikeDtoPostToLike(post));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/questions/{question-id}/likes/{member-id}")
    public void deleteLike(@PathVariable("member-id") @Positive long memberId,
                           @PathVariable("question-id") Long questionId) {
        likeService.delete(memberId, questionId);
    }
}
