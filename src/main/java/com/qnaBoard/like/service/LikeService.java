package com.qnaBoard.like.service;

import com.qnaBoard.exception.CustomException;
import com.qnaBoard.exception.ExceptionCode;
import com.qnaBoard.like.entity.Like;
import com.qnaBoard.like.repository.LikeRepository;
import com.qnaBoard.member.entity.Member;
import com.qnaBoard.member.service.MemberService;
import com.qnaBoard.question.entity.Question;
import com.qnaBoard.question.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    private final MemberService memberService;
    private final QuestionService questionService;
    private final LikeRepository likeRepository;

    public LikeService(MemberService memberService, QuestionService questionService, LikeRepository likeRepository) {
        this.memberService = memberService;
        this.questionService = questionService;
        this.likeRepository = likeRepository;
    }

    public void addLike(Like like) {
        Member member = memberService.findMember(like.getMember().getMemberId());
        Question question = questionService.findQuestion(like.getQuestion().getQuestionId());
        like = Like.of(member, question);
        verifyExistLike(member, question);
        likeRepository.save(like);
    }

    public void delete(long memberId, long questionId) {
        questionService.findQuestion(questionId);
        likeRepository.delete(findVerifyLike(memberId));
    }

    private Like findVerifyLike(long memberId) {
        Optional<Like> optionalLike = likeRepository.findByMember_MemberId(memberId);
        return optionalLike.orElseThrow(() ->
                new CustomException(ExceptionCode.LIKE_NOT_FOUND));
    }

    private void verifyExistLike(Member member, Question question) {
        Optional<Like> optionalLike = likeRepository.findByMemberAndQuestion(member, question);
        if (optionalLike.isPresent()) {
            throw new CustomException(ExceptionCode.LIKE_EXIST);
        }
    }
}
