package com.qnaBoard.member.service;

import com.qnaBoard.member.entity.Member;
import com.qnaBoard.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class memberServiceTest {
    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;

    private Member member;

    @Test
    @DisplayName("중복 회원을 등록하면 MEMBER_EXIST 예외가 발생한다.")
    public void memberExistTest() {
        //given
        BDDMockito.given(memberRepository.findByEmail(Mockito.anyString()))
                .willReturn(Optional.ofNullable(member));
        //when
        //then
        assertThatThrownBy(() -> memberService.createMember(member)).hasMessage("이미 존재하는 회원입니다.");
    }

    @Test
    @DisplayName("회원이 없으면 MEMBER_NOT_FOUND 예외가 발생한다.")
    public void memberNotFoundTest(){
        assertThatThrownBy(() -> memberService.findMember(1L)).hasMessage("존재하지 않는 회원입니다.");
    }
}
