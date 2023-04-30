package com.qnaBoard.answer.service;

import com.qnaBoard.answer.entity.Answer;
import com.qnaBoard.answer.repository.AnswerRepository;
import com.qnaBoard.exception.CustomException;
import com.qnaBoard.exception.ExceptionCode;
import com.qnaBoard.question.entity.Question;
import com.qnaBoard.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository,
                         QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    public Answer createAnswer(Answer answer) {
        verifyExistAnswer(answer.getQuestionId());
        addQuestion(answer);
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer answer) {
        Answer findAnswer = findVerifyAnswer(answer.getAnswerId());
        Optional.ofNullable(findAnswer.getAccess())
                .ifPresent(answer::setAccess);
        return answerRepository.save(answer);
    }

    public Answer findAnswer(long answerId) {
        return findVerifyAnswer(answerId);
    }

    public Page<Answer> findAnswers(int page, int size) {
        return answerRepository.findAll(PageRequest.of(page, size));
    }

    public void deleteAnswer(long answerId) {
        Answer answer = findVerifyAnswer(answerId);
        Question question = questionService.findQuestion(answer.getQuestionId());
        question.setAnswer(null);
        question.setQuestionStatus(Question.QuestionStatus.QUESTION_REGISTRATION);
        answerRepository.delete(findVerifyAnswer(answerId));
    }

    private void addQuestion(Answer answer) {
        Question question = new Question();
        question.setQuestionId(answer.getQuestionId());
        question.setQuestionStatus(Question.QuestionStatus.QUESTION_ANSWERED);
        question.setAnswer(answer);
        questionService.updateQuestion(question, question.getMember());
        answer.setAccess(question.getAccess());
    }

    private void verifyExistAnswer(long questionId) {
        Question question = questionService.findQuestion(questionId);
        if (question.getAnswer() != null) {
            throw new CustomException(ExceptionCode.ANSWER_EXIST);
        }
    }

    private Answer findVerifyAnswer(long answerId) {
        Optional<Answer> findAnswer = answerRepository.findById(answerId);
        return findAnswer.orElseThrow(() ->
                new CustomException(ExceptionCode.ANSWER_NOT_FOUND));
    }
}
