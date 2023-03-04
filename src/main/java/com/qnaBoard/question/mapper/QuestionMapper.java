package com.qnaBoard.question.mapper;

import com.qnaBoard.member.entity.Member;
import com.qnaBoard.question.dto.QuestionPatchDto;
import com.qnaBoard.question.dto.QuestionResponseDto;
import com.qnaBoard.question.entity.Question;
import com.qnaBoard.question.dto.QuestionPostDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    default Question questionPostDtoToQuestion(QuestionPostDto questionPostDto) {
        if (questionPostDto == null) {
            return null;
        }

        Question question = new Question();
        Member member = new Member();
        member.setMemberId(questionPostDto.getMemberId());
        question.setMember(member);
        question.setTitle(questionPostDto.getTitle());
        question.setContent(questionPostDto.getContent());
        question.setAccess(questionPostDto.getAccess());

        return question;
    }

    default Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto, long questionId){
        if (questionPatchDto == null) {
            return null;
        }

        Question question = new Question();
        Member member = new Member();
        member.setMemberId(questionPatchDto.getMemberId());
        question.setQuestionId(questionId);
        question.setMember(member);
        question.setTitle(questionPatchDto.getTitle());
        question.setContent(questionPatchDto.getContent());
        question.setAccess(questionPatchDto.getAccess());

        return question;
    }
    default QuestionResponseDto questionToQuestionResponseDto(Question question) {
        if (question == null) {
            return null;
        }
        Long questionId = question.getQuestionId();
        String title = question.getTitle();
        String content = question.getContent();
        Question.QuestionStatus questionStatus = question.getQuestionStatus();
        String username = question.getMember().getUsername();
        Integer views = question.getView();
        Question.Access access = question.getAccess();
        QuestionResponseDto questionResponseDto = new QuestionResponseDto(questionId, title, content, username, views, questionStatus, access);
        return questionResponseDto;
    }
    List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions);
}
