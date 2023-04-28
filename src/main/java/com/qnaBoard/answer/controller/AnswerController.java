package com.qnaBoard.answer.controller;

import com.qnaBoard.answer.dto.AnswerDto;
import com.qnaBoard.answer.entity.Answer;
import com.qnaBoard.answer.mapper.AnswerMapper;
import com.qnaBoard.answer.service.AnswerService;
import com.qnaBoard.dto.MultiResponseDto;
import com.qnaBoard.dto.SingleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

import static com.qnaBoard.utils.Constant.DEFAULT_ANSWER_URI;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerMapper answerMapper;

    public AnswerController(AnswerService answerService, AnswerMapper answerMapper) {
        this.answerService = answerService;
        this.answerMapper = answerMapper;
    }

    @PostMapping
    public ResponseEntity<Answer> postAnswer(@RequestBody AnswerDto.Post answerDtoPost) {
        answerService.createAnswer(answerMapper.answerPostDtoToAnswer(answerDtoPost));
        URI uri = UriComponentsBuilder.newInstance().build(DEFAULT_ANSWER_URI);
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{answer-id}")
    public ResponseEntity<SingleResponseDto<AnswerDto.Response>> patchAnswer(@PathVariable("answer-id") @Positive long answerId,
                                                                             @RequestBody AnswerDto.Patch answerDtoPatch) {
        answerDtoPatch.setAnswerId(answerId);
        Answer answer = answerService.updateAnswer(answerMapper.answerPatchDtoToAnswer(answerDtoPatch));
        AnswerDto.Response response = answerMapper.answerToAnswerDtoResponse(answer);
        return ResponseEntity.ok(new SingleResponseDto<>(response));
    }

    @GetMapping("/{answer-id}")
    public ResponseEntity<SingleResponseDto<AnswerDto.Response>> getAnswer(@PathVariable("answer-id") @Positive long answerId) {
        Answer answer = answerService.findAnswer(answerId);
        AnswerDto.Response response = answerMapper.answerToAnswerDtoResponse(answer);
        return ResponseEntity.ok(new SingleResponseDto<>(response));
    }

    @GetMapping
    public ResponseEntity<MultiResponseDto<AnswerDto.Response>> getAnswers(@RequestParam @Positive int page,
                                                                           @RequestParam @Positive int size) {
        Page<Answer> answerPage = answerService.findAnswers(page - 1, size);
        List<AnswerDto.Response> responses = answerMapper.answersToAnswerDtoResponses(answerPage.getContent());
        return ResponseEntity.ok(new MultiResponseDto<>(responses, answerPage));
    }

    @DeleteMapping("/{answer-id}")
    public ResponseEntity<Answer> deleteAnswer(@PathVariable("answer-id") @Positive long answerId) {
        answerService.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }
}
