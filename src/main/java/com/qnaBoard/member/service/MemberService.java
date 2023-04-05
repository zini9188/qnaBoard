package com.qnaBoard.member.service;

import com.qnaBoard.exception.CustomException;
import com.qnaBoard.exception.ExceptionCode;
import com.qnaBoard.member.entity.Member;
import com.qnaBoard.member.repository.MemberRepository;
import com.qnaBoard.utils.CustomAuthorityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

    public MemberService(MemberRepository memberRepository,
                         PasswordEncoder passwordEncoder,
                         CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
    }

    public Member createMember(Member member) {
        verifyExistMember(member.getEmail());
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);
        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);
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
        return findVerifyMember(memberId);
    }

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size));
    }

    public void deleteMember(long memberId) {
        Member findMember = findVerifyMember(memberId);
        findMember.setMemberStatus(Member.MemberStatus.MEMBER_DISABLE);
        memberRepository.save(findMember);
    }

    private void verifyExistMember(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new CustomException(ExceptionCode.MEMBER_EXIST);
    }

    private Member findVerifyMember(long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        return member.orElseThrow(() ->
                new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
