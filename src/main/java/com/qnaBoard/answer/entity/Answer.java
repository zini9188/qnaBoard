package com.qnaBoard.answer.entity;

import com.qnaBoard.entity.BaseEntity;
import com.qnaBoard.question.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    @Column(nullable = false)
    private Long questionId;
    @Column(nullable = false)
    private String content;
    private Question.Access access;
}
