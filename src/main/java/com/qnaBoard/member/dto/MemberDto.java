package com.qnaBoard.member.dto;

import com.qnaBoard.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class MemberDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotBlank
        private String email;
        @NotBlank
        private String nickname;
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Patch {
        private Long memberId;
        private String nickname;
        private String username;
        private String password;
        private Member.MemberStatus memberStatus;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long memberId;
        private String email;
        private String nickname;
        private String username;
        private String password;
        private Member.MemberStatus memberStatus;

        public String getMemberStatus() {
            return memberStatus.getStatus();
        }
    }
}
