package com.qnaBoard.member.controller;

import com.qnaBoard.dto.MultiResponseDto;
import com.qnaBoard.dto.SingleResponseDto;
import com.qnaBoard.member.dto.MemberDto;
import com.qnaBoard.member.entity.Member;
import com.qnaBoard.member.mapper.MemberMapper;
import com.qnaBoard.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final String DEFAULT_MEMBER_URI = "/members";

    public MemberController(MemberService memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post postDto) {
        memberService.createMember(memberMapper.memberDtoPostToMember(postDto));
        URI uri = UriComponentsBuilder.newInstance().build(DEFAULT_MEMBER_URI);
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestBody MemberDto.Patch patchDto) {
        patchDto.setMemberId(memberId);
        Member member = memberService.updateMember(memberMapper.memberDtoPatchToMember(patchDto));
        MemberDto.Response response = memberMapper.memberToMemberDtoResponse(member);
        return ResponseEntity.ok(new SingleResponseDto<>(response));
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId) {
        Member member = memberService.findMember(memberId);
        MemberDto.Response response = memberMapper.memberToMemberDtoResponse(member);
        return ResponseEntity.ok(new SingleResponseDto<>(response));
    }

    @GetMapping
    public ResponseEntity getMembers(@RequestParam @Positive int page,
                                     @RequestParam @Positive int size) {
        Page<Member> memberPage = memberService.findMembers(page - 1, size);
        List<MemberDto.Response> responses = memberMapper.membersToResponseMembers(memberPage.getContent());
        return ResponseEntity.ok(new MultiResponseDto<>(responses, memberPage));
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }
}
