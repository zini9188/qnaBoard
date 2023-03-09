package com.qnaBoard.answer.repository;

import com.qnaBoard.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByQuestionId(long questionId);
}
