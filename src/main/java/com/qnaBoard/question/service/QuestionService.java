package com.qnaBoard.question.service;

import com.qnaBoard.exception.CustomException;
import com.qnaBoard.exception.ExceptionCode;
import com.qnaBoard.member.entity.Member;
import com.qnaBoard.member.service.MemberService;
import com.qnaBoard.question.entity.Question;
import com.qnaBoard.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Question updateQuestion(Question question, Member member) {
        Question findQuestion = findVerifyQuestion(question.getQuestionId());
        memberService.verifySameMember(findQuestion.getMember(), member);
        isUpdatable(findQuestion.getQuestionStatus());
        Optional.ofNullable(question.getTitle())
                .ifPresent(findQuestion::setTitle);
        Optional.ofNullable(question.getContent())
                .ifPresent(findQuestion::setContent);
        Optional.ofNullable(question.getView())
                .ifPresent(findQuestion::setView);
        Optional.ofNullable(question.getQuestionStatus())
                .ifPresent(findQuestion::setQuestionStatus);
        Optional.ofNullable(question.getAnswer())
                .ifPresent(findQuestion::setAnswer);
        Optional.ofNullable(question.getAccess())
                .ifPresent(findQuestion::setAccess);
        return questionRepository.save(findQuestion);
    }

    private void isUpdatable(Question.QuestionStatus questionStatus) {
        if (!questionStatus.equals(Question.QuestionStatus.QUESTION_REGISTRATION)) {
            throw new CustomException(ExceptionCode.CANNOT_UPDATE);
        }
    }

    public Question findQuestion(long questionId) {
        Question findQuestion = findVerifyQuestion(questionId);
        verifyActiveMember(findQuestion);
        return findQuestion;
    }

    private static void verifyActiveMember(Question findQuestion) {
        if(findQuestion.getMember().getMemberStatus() == Member.MemberStatus.MEMBER_DISABLE){
            throw new CustomException(ExceptionCode.MEMBER_IS_DISABLE);
        }
    }

    public Page<Question> findQuestions(int page, int size, String type) {
        Question.SortType sortType = Question.SortType.valueOf(type.toUpperCase().replace("-", "_"));
        Sort sort = Sort.by(sortType.getDirection(), sortType.getColumn());
        return questionRepository.findAllByMember_MemberStatus(PageRequest.of(page, size, sort), Member.MemberStatus.MEMBER_ACTIVE);
    }

    public void deleteQuestion(long questionId) {
        Question findQuestion = findVerifyQuestion(questionId);
        findQuestion.setQuestionStatus(Question.QuestionStatus.QUESTION_DELETE);
        questionRepository.save(findQuestion);
    }

    private void verifyQuestion(Question question) {
        Member member = memberService.findMember(question.getMember().getMemberId());
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

    private Question findVerifyQuestion(long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        return question.orElseThrow(() ->
                new CustomException(ExceptionCode.QUESTION_NOT_FOUND));
    }
}