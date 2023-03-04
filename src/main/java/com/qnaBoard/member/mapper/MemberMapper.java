package com.qnaBoard.member.mapper;

import com.qnaBoard.member.dto.MemberPatchDto;
import com.qnaBoard.member.dto.MemberPostDto;
import com.qnaBoard.member.dto.MemberResponseDto;
import com.qnaBoard.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDtoToMember(MemberPostDto memberPostDto);

    Member memberPatchDtoToMember(MemberPatchDto memberPatchDto);
    MemberResponseDto memberToMemberResponseDto(Member member);

    List<MemberResponseDto> membersToResponseMembers(List<Member> members);
}
