package com.qnaBoard.answer.entity;

import com.qnaBoard.question.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Long questionId;
    @Column(nullable = false)
    private String content;
    private Question.Access access;
    private LocalDateTime createdAt = LocalDateTime.now();
}
