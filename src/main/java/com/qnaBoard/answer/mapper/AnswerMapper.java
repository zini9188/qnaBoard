package com.qnaBoard.answer.mapper;

import com.qnaBoard.answer.dto.AnswerDto;
import com.qnaBoard.answer.entity.Answer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    Answer answerPostDtoToAnswer(AnswerDto.Post answerDtoPost);
    Answer answerPatchDtoToAnswer(AnswerDto.Patch answerDtoPatch);
    List<AnswerDto.Response> answersToAnswerDtoResponses(List<Answer> answers);
    AnswerDto.Response answerToAnswerDtoResponse(Answer answer);
}
