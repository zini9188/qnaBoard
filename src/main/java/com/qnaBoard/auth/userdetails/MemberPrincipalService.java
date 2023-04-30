package com.qnaBoard.auth.userdetails;

import com.qnaBoard.exception.CustomException;
import com.qnaBoard.exception.ExceptionCode;
import com.qnaBoard.member.entity.Member;
import com.qnaBoard.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MemberPrincipalService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberPrincipalService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByEmail(username);
        Member findMember = optionalMember.orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
        return new MemberPrincipal(findMember);
    }
}
