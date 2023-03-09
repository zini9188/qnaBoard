package com.qnaBoard.member.repository;

import com.qnaBoard.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>  {
    Optional<Member> findByEmail(String email);
}
