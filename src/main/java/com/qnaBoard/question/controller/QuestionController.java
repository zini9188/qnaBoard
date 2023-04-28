package com.qnaBoard.question.controller;

import com.qnaBoard.dto.MultiResponseDto;
import com.qnaBoard.dto.SingleResponseDto;
import com.qnaBoard.question.dto.QuestionDto;
import com.qnaBoard.question.entity.Question;
import com.qnaBoard.question.mapper.QuestionMapper;
import com.qnaBoard.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

import static com.qnaBoard.utils.Constant.DEFAULT_QUESTION_URI;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    public QuestionController(QuestionService questionService, QuestionMapper questionMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
    }

    @PostMapping
    public ResponseEntity<Question> postQuestion(@RequestBody QuestionDto.Post postDto) {
        questionService.createQuestion(questionMapper.questionPostDtoToQuestion(postDto));
        URI uri = UriComponentsBuilder.newInstance().build(DEFAULT_QUESTION_URI);
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{question-id}")
    public ResponseEntity<SingleResponseDto<QuestionDto.Response>> patchQuestion(@PathVariable("question-id") @Positive long questionId,
                                        @RequestBody QuestionDto.Patch patchDto) {
        Question question = questionService.updateQuestion(questionMapper.questionPatchDtoToQuestion(patchDto, questionId));
        QuestionDto.Response response = questionMapper.questionToQuestionDtoResponse(question);
        return ResponseEntity.ok(new SingleResponseDto<>(response));
    }

    @GetMapping("/{question-id}")
    public ResponseEntity<SingleResponseDto<QuestionDto.Response>> getQuestion(@PathVariable("question-id") @Positive long questionId) {
        Question question = questionService.findQuestion(questionId);
        QuestionDto.Response response = questionMapper.questionToQuestionDtoResponse(question);
        return ResponseEntity.ok(new SingleResponseDto<>(response));
    }

    @GetMapping
    public ResponseEntity<MultiResponseDto<QuestionDto.Response>> getQuestions(@RequestParam @Positive int page,
                                       @RequestParam @Positive int size,
                                       @RequestParam @Positive String type) {
        Page<Question> questionPage = questionService.findQuestions(page - 1, size, type);
        List<QuestionDto.Response> responses = questionMapper.questionsToQuestionDtoResponses(questionPage.getContent());
        return ResponseEntity.ok(new MultiResponseDto<>(responses, questionPage));
    }

    @DeleteMapping("/{question-id}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable("question-id") @Positive long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}
