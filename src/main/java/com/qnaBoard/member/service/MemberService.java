package com.qnaBoard.member.service;

import com.qnaBoard.member.entity.Member;
import com.qnaBoard.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public Member updateMember(Member member) {
        Member findMember = findVerifyMember(member.getMemberId());
        Optional.ofNullable(member.getPassword())
                .ifPresent(findMember::setPassword);
        Optional.ofNullable(member.getNickname())
                .ifPresent(findMember::setNickname);
        Optional.ofNullable(member.getUsername())
                .ifPresent(findMember::setUsername);
        Optional.ofNullable(member.getMemberStatus())
                .ifPresent(findMember::setMemberStatus);
        return memberRepository.save(findMember);
    }

    public Member findMember(long memberId) {
        Member findMember = findVerifyMember(memberId);
        return findMember;
    }

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size));
    }

    public void deleteMember(long memberId) {
        Member findMember = findVerifyMember(memberId);
        findMember.setMemberStatus(Member.MemberStatus.MEMBER_DISABLE);
        memberRepository.save(findMember);
    }

    public Member findVerifyMember(long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        //TODO: 예외 처리 추가 작성
        return member.orElse(null);
    }
}
