package com.qnaBoard.answer.controller;

import com.qnaBoard.answer.dto.AnswerDto;
import com.qnaBoard.answer.entity.Answer;
import com.qnaBoard.answer.mapper.AnswerMapper;
import com.qnaBoard.answer.service.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerService answerService;
    private final AnswerMapper mapper;

    public AnswerController(AnswerService answerService, AnswerMapper mapper) {
        this.answerService = answerService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postAnswer(@RequestBody AnswerDto.Post answerDtoPost) {
        Answer answer = mapper.answerPostDtoToAnswer(answerDtoPost);
        answerService.createAnswer(answer);
        return new ResponseEntity<>(mapper.answerToAnswerDtoResponse(answer), HttpStatus.CREATED);
    }

    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("answer-id") @Positive long answerId,
                                      @RequestBody AnswerDto.Patch answerDtoPatch) {
        answerDtoPatch.setAnswerId(answerId);
        Answer answer = mapper.answerPatchDtoToAnswer(answerDtoPatch);
        answerService.updateAnswer(answer);
        return new ResponseEntity<>(mapper.answerToAnswerDtoResponse(answer), HttpStatus.OK);
    }

    @GetMapping("/{answer-id}")
    public ResponseEntity getAnswer(@PathVariable("answer-id") @Positive long answerId) {
        Answer answer = answerService.getAnswer(answerId);
        return new ResponseEntity<>(mapper.answerToAnswerDtoResponse(answer), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAnswers() {
        return new ResponseEntity<>(mapper.answersToAnswerDtoResponses(answerService.getAnswers()), HttpStatus.OK);
    }

    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") @Positive long answerId) {
        answerService.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }

}
