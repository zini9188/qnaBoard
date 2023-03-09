package com.qnaBoard.answer.service;

import com.qnaBoard.answer.entity.Answer;
import com.qnaBoard.answer.repository.AnswerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer createAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer getAnswer(long answerId) {
        return findVerifyAnswer(answerId);
    }

    public Page<Answer> getAnswers(int page, int size) {
        return answerRepository.findAll(PageRequest.of(page, size));
    }

    public void deleteAnswer(long answerId) {
        Answer answer = findVerifyAnswer(answerId);
        answerRepository.delete(answer);
    }

    public Answer findVerifyAnswer(long answerId) {
        Optional<Answer> findAnswer = answerRepository.findById(answerId);
        return findAnswer.orElse(null);
    }
}
