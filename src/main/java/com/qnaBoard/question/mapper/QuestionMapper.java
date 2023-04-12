package com.qnaBoard.question.mapper;

import com.qnaBoard.member.entity.Member;
import com.qnaBoard.question.dto.QuestionDto;
import com.qnaBoard.question.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    default Question questionPostDtoToQuestion(QuestionDto.Post questionPostDto) {
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

    default Question questionPatchDtoToQuestion(QuestionDto.Patch questionPatchDto, long questionId) {
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

    default QuestionDto.Response questionToQuestionDtoResponse(Question question) {
        if (question == null) {
            return null;
        }
        return new QuestionDto.Response(
                question.getQuestionId(),
                question.getTitle(),
                question.getContent(),
                question.getMember().getUsername(),
                question.getLikes().size(),
                question.getView(),
                question.getQuestionStatus().getStatus(),
                question.getAccess().getStatus(),
                question.getAnswer(),
                question.getCreateAt(),
                question.getUpdateAt());
    }

    List<QuestionDto.Response> questionsToQuestionDtoResponses(List<Question> questions);
}
