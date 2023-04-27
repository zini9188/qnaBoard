package com.qnaBoard.question.repository;

import com.qnaBoard.member.entity.Member;
import com.qnaBoard.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAllByMember_MemberStatus(Pageable pageable, Member.MemberStatus memberStatus);
    Question findByMember_MemberStatus(Member.MemberStatus memberStatus);
}
