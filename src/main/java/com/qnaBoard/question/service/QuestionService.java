package com.qnaBoard.question.service;

import com.qnaBoard.member.entity.Member;
import com.qnaBoard.member.service.MemberService;
import com.qnaBoard.question.entity.Question;
import com.qnaBoard.question.repository.QuestionRepository;
import com.sun.xml.bind.v2.TODO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;
    private MemberService memberService;

    public QuestionService(QuestionRepository questionRepository, MemberService memberService) {
        this.questionRepository = questionRepository;
        this.memberService = memberService;
    }

    public Question createQuestion(Question question) {
        verifyQuestion(question);
        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question) {
        // 같은 사용자가 작성하였는지 확인
        Question findQuestion = findVerifyQuestion(question.getQuestionId());
        // 오류가 발생하지 않았다면
        verifyEqualsUser(findQuestion, question);
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

    private void verifyEqualsUser(Question findQuestion, Question question) {
        if (!Objects.equals(findQuestion.getMember().getMemberId(), question.getMember().getMemberId())) {
            //TODO: 사용자 다름 코드 메세지
            new RuntimeException();
        }
    }

    public void verifyQuestion(Question question) {
        // 멤버 존재하는지 검증 후 질문 등록
        Member member = memberService.findVerifyMember(question.getMember().getMemberId());
        member.addPost(question);
        question.addMember(member);
    }

    public Question findVerifyQuestion(long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        //TODO: 예외 처리 추가 작성
        return question.orElse(null);
    }

}
