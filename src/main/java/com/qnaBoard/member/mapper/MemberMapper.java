package com.qnaBoard.member.mapper;

import com.qnaBoard.member.dto.MemberDto;
import com.qnaBoard.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member memberDtoPostToMember(MemberDto.Post memberDtoPost);
    Member memberDtoPatchToMember(MemberDto.Patch memberDtoPatch);
    MemberDto.Response memberToMemberDtoResponse(Member member);
    List<MemberDto.Response> membersToResponseMembers(List<Member> members);
}
