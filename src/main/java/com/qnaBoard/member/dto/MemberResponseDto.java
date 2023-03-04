package com.qnaBoard.member.dto;

import com.qnaBoard.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private Long memberId;
    private String email;
    private String nickname;
    private String username;
    private String password;
    private Member.MemberStatus memberStatus;
}
