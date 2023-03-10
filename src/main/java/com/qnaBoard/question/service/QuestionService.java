package com.qnaBoard.question.service;

import com.qnaBoard.answer.entity.Answer;
import com.qnaBoard.answer.service.AnswerService;
import com.qnaBoard.exception.CustomException;
import com.qnaBoard.exception.ExceptionCode;
import com.qnaBoard.member.entity.Member;
import com.qnaBoard.member.service.MemberService;
import com.qnaBoard.question.entity.Question;
import com.qnaBoard.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final MemberService memberService;

    public QuestionService(QuestionRepository questionRepository,
                           MemberService memberService) {
        this.questionRepository = questionRepository;
        this.memberService = memberService;
    }

    public Question createQuestion(Question question) {
        verifyQuestion(question);
        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question) {
        Question findQuestion = findVerifyQuestion(question.getQuestionId());
        Optional.ofNullable(question.getTitle())
                .ifPresent(findQuestion::setTitle);
        Optional.ofNullable(question.getContent())
                .ifPresent(findQuestion::setContent);
        Optional.ofNullable(question.getView())
                .ifPresent(findQuestion::setView);
        Optional.ofNullable(question.getAccess())
                .ifPresent(findQuestion::setAccess);
        Optional.ofNullable(question.getQuestionStatus())
                .ifPresent(findQuestion::setQuestionStatus);
        Optional.ofNullable(question.getAnswer())
                .ifPresent(findQuestion::setAnswer);
        return questionRepository.save(findQuestion);
    }

    public Question findQuestion(long questionId) {
        return findVerifyQuestion(questionId);
    }

    public Page<Question> findQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size));
    }

    public void deleteQuestion(long questionId) {
        Question findQuestion = findVerifyQuestion(questionId);
        findQuestion.setQuestionStatus(Question.QuestionStatus.QUESTION_DELETE);
        questionRepository.save(findQuestion);
    }

    private void verifyQuestion(Question question) {
        Member member = memberService.findMember(question.getMember().getMemberId());
        isEqualWriter(question.getMember().getMemberId(), member.getMemberId());
        isDeleted(question.getQuestionStatus());
        isAnswered(question.getQuestionStatus());
        member.addQuestion(question);
        question.addMember(member);
    }

    private static void isAnswered(Question.QuestionStatus questionStatus) {
        if (questionStatus.equals(Question.QuestionStatus.QUESTION_ANSWERED)) {
            throw new CustomException(ExceptionCode.ANSWERED_QUESTION);
        }
    }

    private static void isDeleted(Question.QuestionStatus questionStatus) {
        if (questionStatus.equals(Question.QuestionStatus.QUESTION_DELETE)) {
            throw new CustomException(ExceptionCode.DELETED_QUESTION);
        }
    }

    private static void isEqualWriter(long questionId, long memberId) {
        // TODO Spring Security 적용 이후 변경
        if (questionId != memberId) {
            throw new CustomException(ExceptionCode.NOT_AUTHOR_QUESTION);
        }
    }

    private Question findVerifyQuestion(long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        return question.orElseThrow(() ->
                new CustomException(ExceptionCode.QUESTION_NOT_FOUND));
    }
}