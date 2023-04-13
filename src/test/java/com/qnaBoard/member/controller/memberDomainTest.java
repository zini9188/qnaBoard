package com.qnaBoard.member.controller;

import com.google.gson.Gson;
import com.qnaBoard.member.dto.MemberDto;
import com.qnaBoard.member.entity.Member;
import com.qnaBoard.member.mapper.MemberMapper;
import com.qnaBoard.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static com.qnaBoard.utils.Constant.DEFAULT_MEMBER_URI;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class memberDomainTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private MemberService memberService;
    @MockBean
    private MemberMapper memberMapper;

    @Test
    @DisplayName("회원 정보를 등록")
    public void postMember() throws Exception {
        MemberDto.Post post = new MemberDto.Post(
                "test@naver.com",
                "테스트닉네임",
                "테스트이름",
                "test!@#");
        String content = gson.toJson(post);

        given(memberService.createMember(Mockito.any(Member.class)))
                .willReturn(new Member());

        ResultActions actions = mockMvc.perform(
                post(DEFAULT_MEMBER_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );
        actions.andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 정보를 수정")
    public void patchMember() throws Exception {
        MemberDto.Patch patch = new MemberDto.Patch(
                1L,
                "테스트닉네임",
                "테스트이름",
                "test!@#",
                Member.MemberStatus.MEMBER_ACTIVE);
        String content = gson.toJson(patch);

        MemberDto.Response response = new MemberDto.Response(
                1L,
                "test@naver.com",
                "테스트닉네임",
                "테스트이름",
                "test!@#",
                Member.MemberStatus.MEMBER_ACTIVE);

        given(memberMapper.memberDtoPatchToMember(Mockito.any(MemberDto.Patch.class)))
                .willReturn(new Member());
        given(memberService.updateMember(Mockito.any(Member.class)))
                .willReturn(new Member());
        given(memberMapper.memberToMemberDtoResponse(Mockito.any(Member.class)))
                .willReturn(response);

        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.patch(DEFAULT_MEMBER_URI + "/{member-id}", patch.getMemberId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );
        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(response.getMemberId()))
                .andExpect(jsonPath("$.data.email").value(response.getEmail()))
                .andExpect(jsonPath("$.data.nickname").value(response.getNickname()))
                .andExpect(jsonPath("$.data.username").value(response.getUsername()))
                .andExpect(jsonPath("$.data.password").value(response.getPassword()))
                .andExpect(jsonPath("$.data.memberStatus").value(response.getMemberStatus()))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 정보를 조회")
    public void getMember() throws Exception {
        MemberDto.Response response = new MemberDto.Response(
                1L,
                "test@naver.com",
                "테스트닉네임",
                "테스트이름",
                "test!@#",
                Member.MemberStatus.MEMBER_ACTIVE);

        given(memberService.findMember(Mockito.anyLong()))
                .willReturn(new Member());
        given(memberMapper.memberToMemberDtoResponse(Mockito.any(Member.class)))
                .willReturn(response);

        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_MEMBER_URI + "/{member-id}", response.getMemberId())
        );
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(response.getMemberId()))
                .andExpect(jsonPath("$.data.email").value(response.getEmail()))
                .andExpect(jsonPath("$.data.nickname").value(response.getNickname()))
                .andExpect(jsonPath("$.data.username").value(response.getUsername()))
                .andExpect(jsonPath("$.data.password").value(response.getPassword()))
                .andExpect(jsonPath("$.data.memberStatus").value(response.getMemberStatus()))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지별 회원 정보를 조회")
    public void getMembers() throws Exception {
        List<Member> members = List.of(
                new Member(),
                new Member()
        );
        List<MemberDto.Response> responses = List.of(
                new MemberDto.Response(
                        1L,
                        "test1@naver.com",
                        "테스트닉네임1",
                        "테스트이름2",
                        "test!@#",
                        Member.MemberStatus.MEMBER_ACTIVE),
                new MemberDto.Response(
                        2L,
                        "test2@naver.com",
                        "테스트닉네임1",
                        "테스트이름2",
                        "test!@#",
                        Member.MemberStatus.MEMBER_ACTIVE)
        );
        Page<Member> memberPage = new PageImpl<>(members);

        given(memberService.findMembers(Mockito.anyInt(), Mockito.anyInt()))
                .willReturn(memberPage);
        given(memberMapper.membersToResponseMembers(Mockito.any(List.class)))
                .willReturn(responses);

        String page = "1";
        String size = "10";
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("page", page);
        multiValueMap.add("size", size);

        mockMvc.perform(
                        RestDocumentationRequestBuilders.get(DEFAULT_MEMBER_URI)
                                .params(multiValueMap)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(is(2)))
                .andExpect(jsonPath("$.data[0].memberId").value(responses.get(0).getMemberId()))
                .andExpect(jsonPath("$.data[0].email").value(responses.get(0).getEmail()))
                .andExpect(jsonPath("$.data[0].nickname").value(responses.get(0).getNickname()))
                .andExpect(jsonPath("$.data[0].username").value(responses.get(0).getUsername()))
                .andExpect(jsonPath("$.data[0].password").value(responses.get(0).getPassword()))
                .andExpect(jsonPath("$.data[0].memberStatus").value(responses.get(0).getMemberStatus()))
                .andExpect(jsonPath("$.pageInfo.page").value(page))
                .andExpect(jsonPath("$.pageInfo.size").value(2))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 정보를 삭제")
    public void deleteMember() throws Exception {
        doNothing().when(memberService).deleteMember(Mockito.anyLong());
        mockMvc.perform(
                RestDocumentationRequestBuilders.delete(DEFAULT_MEMBER_URI + "/{member-id}", 1)
        ).andExpect(status().isNoContent());
    }
}
