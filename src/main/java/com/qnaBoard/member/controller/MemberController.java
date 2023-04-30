package com.qnaBoard.member.controller;

import com.qnaBoard.auth.userdetails.MemberPrincipal;
import com.qnaBoard.dto.MultiResponseDto;
import com.qnaBoard.dto.SingleResponseDto;
import com.qnaBoard.member.dto.MemberDto;
import com.qnaBoard.member.entity.Member;
import com.qnaBoard.member.mapper.MemberMapper;
import com.qnaBoard.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

import static com.qnaBoard.utils.Constant.DEFAULT_MEMBER_URI;

@RestController
@Validated
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    public MemberController(MemberService memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    @PostMapping
    public ResponseEntity<Member> postMember(@Valid @RequestBody MemberDto.Post postDto) {
        memberService.createMember(memberMapper.memberDtoPostToMember(postDto));
        URI uri = UriComponentsBuilder.newInstance().build(DEFAULT_MEMBER_URI);
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity<SingleResponseDto<MemberDto.Response>> patchMember(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                                                             @PathVariable("member-id") @Positive long memberId,
                                                                             @Valid @RequestBody MemberDto.Patch patchDto) {
        Member requestMember = memberPrincipal.getMember();
        patchDto.setMemberId(memberId);
        Member member = memberService.updateMember(memberMapper.memberDtoPatchToMember(patchDto), requestMember);
        MemberDto.Response response = memberMapper.memberToMemberDtoResponse(member);
        return ResponseEntity.ok(new SingleResponseDto<>(response));
    }

    @GetMapping("/{member-id}")
    public ResponseEntity<SingleResponseDto<MemberDto.Response>> getMember(@PathVariable("member-id") @Positive long memberId) {
        Member member = memberService.findMember(memberId);
        MemberDto.Response response = memberMapper.memberToMemberDtoResponse(member);
        return ResponseEntity.ok(new SingleResponseDto<>(response));
    }

    @GetMapping
    public ResponseEntity<MultiResponseDto<MemberDto.Response>> getMembers(@RequestParam @Positive int page,
                                                                           @RequestParam @Positive int size) {
        Page<Member> memberPage = memberService.findMembers(page - 1, size);
        List<MemberDto.Response> responses = memberMapper.membersToResponseMembers(memberPage.getContent());
        return ResponseEntity.ok(new MultiResponseDto<>(responses, memberPage));
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity<Member> deleteMember(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                               @PathVariable("member-id") @Positive long memberId) {
        memberService.deleteMember(memberId, memberPrincipal.getMember());
        return ResponseEntity.noContent().build();
    }
}
