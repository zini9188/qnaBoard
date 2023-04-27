package com.qnaBoard.like.mapper;

import com.qnaBoard.like.dto.LikeDto;
import com.qnaBoard.like.entity.Like;
import com.qnaBoard.member.entity.Member;
import com.qnaBoard.question.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    default Like LikeDtoPostToLike(LikeDto.Post post) {
        Member member = new Member();
        Question question = new Question();
        member.setMemberId(post.getMemberId());
        question.setQuestionId(post.getQuestionId());
        return Like.of(member, question);
    }
}
