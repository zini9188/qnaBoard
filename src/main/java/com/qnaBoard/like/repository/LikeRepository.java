package com.qnaBoard.like.repository;

import com.qnaBoard.like.entity.Like;
import com.qnaBoard.member.entity.Member;
import com.qnaBoard.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByMemberAndQuestion(Member member, Question question);
    Optional<Like> findByMember_MemberId(Long memberId);
}
