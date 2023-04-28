package com.qnaBoard.question.controller;

import com.qnaBoard.answer.dto.AnswerDto;
import com.qnaBoard.answer.entity.Answer;
import com.qnaBoard.answer.mapper.AnswerMapper;
import com.qnaBoard.answer.service.AnswerService;
import com.qnaBoard.dto.MultiResponseDto;
import com.qnaBoard.dto.SingleResponseDto;
import com.qnaBoard.question.dto.QuestionDto;
import com.qnaBoard.question.entity.Question;
import com.qnaBoard.question.mapper.QuestionMapper;
import com.qnaBoard.question.service.QuestionService;
import com.qnaBoard.utils.UriCreator;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final AnswerService answerService;
    private final AnswerMapper answerMapper;

    public QuestionController(QuestionService questionService,
                              QuestionMapper questionMapper,
                              AnswerService answerService,
                              AnswerMapper answerMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
        this.answerService = answerService;
        this.answerMapper = answerMapper;
    }

    @PostMapping
    public ResponseEntity<Question> postQuestion(@RequestBody QuestionDto.Post postDto) {
        Question question = questionService.createQuestion(questionMapper.questionPostDtoToQuestion(postDto));
        return ResponseEntity.created(UriCreator.createUri(question.getQuestionId())).build();
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

    @PostMapping("/{question-id}/answers")
    public ResponseEntity<Answer> postAnswer(@PathVariable("question-id") Long questionId,
                                             @RequestBody AnswerDto.Post answerDtoPost) {
        answerDtoPost.addQuestionId(questionId);
        Answer answer = answerService.createAnswer(answerMapper.answerPostDtoToAnswer(answerDtoPost));
        return ResponseEntity.created(UriCreator.createUri(answer.getAnswerId())).build();
    }
}
